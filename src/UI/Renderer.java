package ui;

import core.GameState;
import entity.Player;
import entity.Wolf;
import entity.Tree;
import input.InputHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import  core.GameConfig;
import javafx.scene.image.Image;
import java.util.List;

public class Renderer {



    private final Canvas canvas;  // Là vùng để vẽ game
    private final GraphicsContext graphicsContext;   // là cây bút để vẽ canvas
    private final Hud hud;

    private final Image[] wolfRunFrames; // mang chua 4 frame anh
    private int wolfFrameIndex; //dang ve frame nao, vi du 0,1,2,3
    private long lastWolfFrameTime;  // lan cuoi doi frame
    private static final long WOLF_FRAME_DURATION_NS = 120_000_000L; // 120ms= 0,12s
    private final Image backgroundImage;


    public Renderer(Stage stage, InputHandler inputHandler) {
        this.canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.hud= new Hud();
        this.wolfRunFrames = new Image[]{
                new Image("file:assets/wolf_run/wolf_run_0001.png"),
                new Image("file:assets/wolf_run/wolf_run_0002.png"),
                new Image("file:assets/wolf_run/wolf_run_0003.png"),
                new Image("file:assets/wolf_run/wolf_run_0004.png"),
                new Image("file:assets/wolf_run/wolf_run_0005.png"),
                new Image("file:assets/wolf_run/wolf_run_0006.png")

        };
        this.wolfFrameIndex = 0;
        this.lastWolfFrameTime = 0;
        this.backgroundImage = new Image("file:assets/backgrounds/grass03.png");


        StackPane root = new StackPane(canvas);// tạo 1 StackPane và đặt canvas trong nó
        // StackPane là 1 layout container của JVFX để quản lý các node con
        // ko đưa canvas vào thẳng scene vì scene thường nhận 1 root node, và StackPane là root đó

        Scene scene = new Scene(root, GameConfig.WIDTH,GameConfig.HEIGHT);
        inputHandler.attach(scene);
        //gán input vào scene, kiểu hãy đọc input trên scene này

        stage.setScene(scene); // đặt scene làm nội dung của cửa sổ stage
        //->> kể từ giờ cửa sổ sẽ hiển thị scene vừa tạo
        stage.show();
    }

    public void render(GameState gameState, Player player, Wolf wolf,long now,boolean wolfMoving, List<Tree> trees,double cameraX, double cameraY) {
        if (backgroundImage.isError()) {
            graphicsContext.setFill(Color.BEIGE);
            graphicsContext.fillRect(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);// neeus backround load lỗi s hiển thị nền này

        } else {
            graphicsContext.drawImage(
                    backgroundImage,
                    0, 0,
                    GameConfig.WIDTH, GameConfig.HEIGHT
            );
        }

        for (Tree tree : trees) {
            double treeScreenX = tree.getX() - cameraX;
            double treeScreenY = tree.getY() - cameraY;

            graphicsContext.setFill(Color.SADDLEBROWN);
            graphicsContext.fillRect(treeScreenX + 24, treeScreenY + 36, 16, 28);

            graphicsContext.setFill(Color.DARKGREEN);
            graphicsContext.fillOval(treeScreenX, treeScreenY, tree.getWidth(), tree.getHeight() - 12);
        }
        //4 dongf treen la ve cay
        graphicsContext.setFill(Color.DARKGREEN);
        graphicsContext.fillText("State: " + gameState, 20, 30);
        graphicsContext.fillText("WASD: move", 20, 55);
        graphicsContext.fillText("J: take damage | K: heal", 20, 80);

        graphicsContext.setFill((Color.DODGERBLUE));
        double playerScreenX = player.getX() - cameraX;
        double playerScreenY = player.getY() - cameraY;

        graphicsContext.fillRect(
                playerScreenX, playerScreenY,
                player.getWidth(), player.getHeight()
        );

        if (!wolfMoving){
            wolfFrameIndex = 0;
        }
        else if (wolfMoving && now - lastWolfFrameTime >= WOLF_FRAME_DURATION_NS) {
            //now - lastWolfFrameTime :Tg đã trôi qua bao lâu từ lần đổi frame trước
            //WOLF_FRAME_DURATION_NS nếu đủ 120ms thì đổi frame
            wolfFrameIndex = (wolfFrameIndex + 1) % wolfRunFrames.length;
            //tăng frame lên 1
            // nếu đg  frame cuối thì quay về frame đầu
            lastWolfFrameTime = now;
            // caaph nhật mốc tgian mới
        }

        boolean facingRight = (player.getX() + player.getWidth() / 2)
                >= (wolf.getX() + wolf.getWidth() / 2);  // nếu player đứng bên phải wolf thì wolf nhìn phải

        //đoạn này để vẽ wolf
        Image currentWolfFrame = wolfRunFrames[wolfFrameIndex];
        // biến tmp để chạy từng ảnh
        double wolfScreenX = wolf.getX() - cameraX;
        double wolfScreenY = wolf.getY() - cameraY;
        if (currentWolfFrame.isError()) {
            graphicsContext.setFill(Color.CRIMSON);
            graphicsContext.fillRect(
                    // ảnh hiện tại cần vẽ
                    wolf.getX(), wolf.getY(),
                    wolf.getWidth(), wolf.getHeight()
            );
        } else {
            if (facingRight) { // khi wolf nhin phai
                graphicsContext.drawImage(
                        currentWolfFrame,
                        wolfScreenX, wolfScreenY,
                        wolf.getWidth(), wolf.getHeight()
                );
            } else { // khi wolf nhin trai
                graphicsContext.save();
                graphicsContext.translate(wolfScreenX + wolf.getWidth(), wolfScreenY);
                graphicsContext.scale(-1, 1);
                graphicsContext.drawImage(
                        currentWolfFrame,
                        0, 0,
                        wolf.getWidth(), wolf.getHeight()
                );
                graphicsContext.restore(); // trả canvas về trạng thái bthg
            }

        }
        hud.render(graphicsContext,player);

        if (gameState == GameState.GAME_OVER) {
            graphicsContext.setFill(Color.DARKRED);
            graphicsContext.fillText("GAME OVER", 400, 250);

            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillText("The wolf caught you.", 380, 280);
            graphicsContext.fillText("Press R to restart.", 385, 310);
        }
    }
}
