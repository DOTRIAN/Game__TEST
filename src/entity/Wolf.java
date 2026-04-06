package entity;

public class Wolf {
    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;

    public Wolf(double x, double y, double width, double height, double speed){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.speed=speed;
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

}

