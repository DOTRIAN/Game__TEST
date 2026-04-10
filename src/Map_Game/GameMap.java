package Map_Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import org.w3c.dom.*;
import javax.xml.parsers.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class GameMap {

    // layer, row, col
    public int[][][] tiles;

    public int tileSize = 32;
    public int width, height;

    private Image tileset;

    // danh sách tile bị chặn
    public Set<Integer> blockedTiles = new HashSet<>();

    // =========================
    // LOAD TILESET IMAGE
    // =========================
    public void loadTileset(String path) {
        tileset = new Image("file:" + path);
    }

    // =========================
    // LOAD TMX + TSX
    // =========================
    public void loadFromTMX(String path) {
        try {
            File file = new File(path);

            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(file);

            doc.getDocumentElement().normalize();

            // ==== MAP INFO ====
            Element mapElement = (Element) doc.getElementsByTagName("map").item(0);

            width = Integer.parseInt(mapElement.getAttribute("width"));
            height = Integer.parseInt(mapElement.getAttribute("height"));
            tileSize = Integer.parseInt(mapElement.getAttribute("tilewidth"));

            // ==== LOAD LAYERS ====
            NodeList layerList = doc.getElementsByTagName("layer");
            int layerCount = layerList.getLength();

            tiles = new int[layerCount][height][width];

            for (int l = 0; l < layerCount; l++) {

                Element layer = (Element) layerList.item(l);
                Node dataNode = layer.getElementsByTagName("data").item(0);

                String csv = dataNode.getTextContent().trim();
                String[] numbers = csv.split(",");

                int index = 0;

                for (int row = 0; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        tiles[l][row][col] = Integer.parseInt(numbers[index].trim());
                        index++;
                    }
                }
            }

            // =========================
            // 🔥 LOAD TSX (collision) - GIỮ parse tự động từ property "blocked" thay "can" (linh hoạt)
            // =========================
            NodeList tilesetList = doc.getElementsByTagName("tileset");

            for (int i = 0; i < tilesetList.getLength(); i++) {

                Element tilesetElement = (Element) tilesetList.item(i);

                String source = tilesetElement.getAttribute("source");

                if (source == null || source.isEmpty()) continue;

                File tsxFile = new File(file.getParent(), source);

                try {
                    Document tsxDoc = DocumentBuilderFactory.newInstance()
                            .newDocumentBuilder()
                            .parse(tsxFile);

                    tsxDoc.getDocumentElement().normalize();

                    NodeList tileList = tsxDoc.getElementsByTagName("tile");

                    for (int j = 0; j < tileList.getLength(); j++) {

                        Element tile = (Element) tileList.item(j);

                        int id = Integer.parseInt(tile.getAttribute("id")) + 1; // Tiled id=0 → gid=1

                        NodeList props = tile.getElementsByTagName("property");

                        for (int k = 0; k < props.getLength(); k++) {

                            Element prop = (Element) props.item(k);

                            String name = prop.getAttribute("name").toLowerCase(); // Linh hoạt "blocked"/"solid"/"can"
                            String value = prop.getAttribute("value").toLowerCase();

                            if ((name.contains("blocked") || name.contains("solid") || name.equals("can")) &&
                                    (value.equals("true") || value.equals("1") || value.equals("on"))) {

                                blockedTiles.add(id);
                            }
                        }
                    }
                } catch (Exception tsxEx) {
                    System.err.println("⚠️ TSX parse fail: " + tsxFile + " → " + tsxEx.getMessage());
                }
            }

            // ✅ FALLBACK nếu TMX/TSX không có property → hardcode map2 common solid
            if (blockedTiles.isEmpty()) {
                blockedTiles.add(82); blockedTiles.add(83); blockedTiles.add(98); blockedTiles.add(99);
                blockedTiles.add(7); blockedTiles.add(34); blockedTiles.add(35); blockedTiles.add(18); blockedTiles.add(19);
                System.out.println("✅ FALLBACK blockedTiles: " + blockedTiles.size() + " hardcoded");
            }

            System.out.println("🔥 Final BlockedTiles: " + blockedTiles.size() + " IDs = " + blockedTiles); // DEBUG

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // RENDER MAP
    // =========================
    public void render(GraphicsContext gc) {

        int tilesetCols = (int) (tileset.getWidth() / tileSize);

        for (int l = 0; l < tiles.length; l++) {

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {

                    int tileID = tiles[l][row][col];

                    if (tileID == 0) continue;

                    int index = tileID - 1;

                    int sx = (index % tilesetCols) * tileSize;
                    int sy = (index / tilesetCols) * tileSize;

                    gc.drawImage(
                            tileset,
                            sx, sy,
                            tileSize, tileSize,
                            col * tileSize,
                            row * tileSize,
                            tileSize, tileSize
                    );
                }
            }
        }
    }

    // =========================
    // CHECK COLLISION
    // =========================
    public boolean isBlocked(int worldX, int worldY) {

        int col = worldX / tileSize;
        int row = worldY / tileSize;

        // ngoài map = chặn
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return true;
        }

        for (int l = 0; l < tiles.length; l++) {

            int tileID = tiles[l][row][col];

            if (blockedTiles.contains(tileID)) {
                return true;
            }
        }

        return false;
    }
}