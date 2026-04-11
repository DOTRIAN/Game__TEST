package entity;
import animation.SpriteAnimation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import animation.SpriteSheetLoader;


public class Wolf {
    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;
    private final SpriteAnimation runAnimation;

    public Wolf(double x, double y, double width, double height, double speed){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.speed=speed;

        this.runAnimation = new SpriteAnimation(
                SpriteSheetLoader.loadGrid("file:assets/wolf.png", 2, 3),
                120_000_000L
        );
    }

    public void moveToward(Player player){
        double playerCenterX = player.getX() + player.getWidth() / 2; // tính điểm trung tâm của player
        double playerCenterY = player.getY() + player.getHeight() / 2;// để bắt được tại trung tâm, ko phải tại góc trái

        double wolfCenterX = x + width / 2;
        double wolfCenterY = y + height / 2;

        double dx = playerCenterX - wolfCenterX;
        double dy = playerCenterY - wolfCenterY;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            x += (dx / distance) * speed; // chuaanr hóa vector dx/distance giúp tốc độ được đảm bảo, và đuổi theo đúng logic
            y += (dy / distance) * speed;
        }

    }
    public void clampPosition (double minX, double minY, double maxWidth, double maxHeight){
        if (x<minX){
            x=minX;
        }

        if (y<minY){
            y=minY;
        }

        if (x+width > maxWidth){
            x = maxWidth-width;
        }
        if (y+height > maxHeight){
            y=maxHeight-height;
        }
    }
    public void reset(double startX, double startY){
        this.x=startX;
        this.y=startY;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }

    public void draw(GraphicsContext graphicsContext, double cameraX, double cameraY, long now, boolean moving, Player player) {
        runAnimation.update(now, moving);

        boolean facingRight = (player.getX() + player.getWidth() / 2)
                >= (x + width / 2);

        Image currentFrame = runAnimation.getCurrentFrame();
        double screenX = x - cameraX;
        double screenY = y - cameraY;

        if (currentFrame.isError()) {
            graphicsContext.setFill(Color.CRIMSON);
            graphicsContext.fillRect(screenX, screenY, width, height);
            return;
        }

        if (facingRight) {
            graphicsContext.drawImage(currentFrame, screenX, screenY, width, height);
        } else {
            graphicsContext.save();
            graphicsContext.translate(screenX + width, screenY);
            graphicsContext.scale(-1, 1);  // kĩ thuật lật ảnh
            graphicsContext.drawImage(currentFrame, 0, 0, width, height);
            graphicsContext.restore();
        }
    }
}

