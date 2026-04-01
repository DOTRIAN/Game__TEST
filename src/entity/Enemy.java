package entity;

public class Enemy {
    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;

    public Enemy(double x,double y,double width,double height,double speed){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.speed=speed;
    }

    public void moveToward(Player player){
        if (player.getX()<x){
            x-= speed;
        }
        else if (player.getX()>x){
            x+=speed;
        }

        if (player.getY()<y){
            y-=speed;
        }
        else if (player.getY()>y){
            y+=speed;
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

