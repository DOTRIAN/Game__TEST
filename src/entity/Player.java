package entity;

public class Player {
    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;
    private int hp;
    private int maxHp;

    public Player (double x, double y, double width, double height, double speed,int maxHp){
        this.x=x;
        this.y=y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.maxHp=maxHp;
        this.hp=maxHp;

    }
    public void moveLeft(){
        x-=speed;
    }

    public void moveRight(){
        x+=speed;
    }


    public void moveUp(){
        y-=speed;
    }

    public void moveDown(){
        y+=speed;
    }

    public void clampPosition (double minX, double minY, double maxWidth, double maxHeight){
       // Clamp la ep gia tri trong khoangr cho phep, neu vuot qua thi ep ve biên hợp lệ gần nhất
        if (x<minX){
            x = minX;
        }

        if (y<minY){
            y=minY;
        }

        if (x+width > maxWidth){
            x = maxWidth - width;
        }

        if (y + height >maxHeight){
            y=maxHeight - height;
        }
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getSpeed() {
        return speed;
    }
    public int getHp(){
        return hp;
    }
    public int getMaxHp(){
        return maxHp;
    }

    public void takeDamage(int amount){
        hp -= amount;

        if (hp<0){
            hp=0;
        }
    }
    // amount: luong dame nhan vao
    public void heal (int amount){
        hp+= amount;
        if (hp>maxHp){
            hp=maxHp;
        }
    }

    public boolean isAlive(){
        return hp>0;
    }

    public void reset(double startX,double startY){
        this.x = startX;
        this.y = startY;
        this.hp = maxHp;
    }

    public void setPosition(double x,double y){
        this.x = x;
        this.y = y;
    } // hàm setter để set vị trí của player

}
