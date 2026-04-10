package entity;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Animation {
    private Image[] frames;
    private int currentFrame=0;// frame hiện tại đang hiển thị
    private int frameCounter=0;// đếm số lần update;
    private int speed;
    
    public Animation(String path, int frameWidth, int frameHeight, int speed) {
Image spriteSheet = new Image("file:assets/" + path.substring(1)); // remove leading /
        PixelReader reader = spriteSheet.getPixelReader();
        int cols = (int)(spriteSheet.getWidth() / frameWidth);
    int rows = (int)(spriteSheet.getHeight() / frameHeight);

    frames = new Image[cols * rows];

    int index = 0;

    for (int y = 0; y < rows; y++) {
        for (int x = 0; x < cols; x++) {

            frames[index++] = new WritableImage(
                reader,
                x * frameWidth,
                y * frameHeight,
                frameWidth,
                frameHeight
            );
        }
    }

    this.speed = speed;
}
public void update(){
        if (frames == null || frames.length == 0) return;
        frameCounter++;
        if(frameCounter>speed){
            currentFrame=(currentFrame+1)%frames.length;
            frameCounter=0;
        }
    }
public Image getCurrentFrame() {
    if (frames == null || frames.length == 0) {
        return null;
    }
    return frames[currentFrame];
}
    
}