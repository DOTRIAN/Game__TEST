package animation;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public final class SpriteSheetLoader {  // ko cho ke thua class nay

    private SpriteSheetLoader() {
    }

    public static Image[] loadGrid(String imagePath, int columns, int rows) {
        Image spriteSheet = new Image(imagePath);
        int frameWidth = (int) spriteSheet.getWidth() / columns;
        int frameHeight = (int) spriteSheet.getHeight() / rows;

        return loadGrid(imagePath, columns, rows, frameWidth, frameHeight);
    }

    public static Image[] loadGrid(String imagePath, int columns, int rows, int frameWidth, int frameHeight) {
        Image spriteSheet = new Image(imagePath);
        PixelReader pixelReader = spriteSheet.getPixelReader();  // cho phep đọc pixel từ ảnh gốc

        Image[] frames = new Image[columns * rows];
        int index = 0;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int x = column * frameWidth;
                int y = row * frameHeight;

                frames[index] = new WritableImage(pixelReader, x, y, frameWidth, frameHeight);
                index++;
            }
        }

        return frames;
    }
}

// tại sao lại có 2 method loadGrid?
// vì overloading, cái 1 dùng khi muốn chia đều, cái 2 dùng khi muốn chỉ định kích cỡ ảnh

