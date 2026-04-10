package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import Map_Game.GameMap;
import input.InputHandler;

public class Player {
    private InputHandler input;
    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;
    private Animation idle;
    private Animation walk;
    private boolean isMoving=false;
    private boolean facingRight=true;
    private Animation current;

    private int maxHealth = 100;
    private int currentHealth = 100;

    private long lastHitTime = 0;

   
    private GameMap map;

    public int getHealth() {
        return currentHealth;
    }

    public void setHealth(int health) {
        this.currentHealth = Math.min(health, maxHealth);
    }

    public void takeDamage(int damage) {
        long now = System.currentTimeMillis();

        if (now - lastHitTime > 1000) {
            currentHealth -= damage;
            lastHitTime = now;
        }
    }

  
    public Player(double x,double y,double width,double height,double speed, GameMap map){
        this.x=x;   
        this.y=y;
        this.width=width;
        this.height=height;
        this.speed=speed;
        this.map = map;

idle = new Animation("idle.png",512, 512, 10);
walk = new Animation("run.png", 512, 512, 6);
        current=idle;
    }

    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getWidth(){ return width; }
    public double getHeight(){ return height; }

    public void setX(double x){ this.x = x; }
    public void setY(double y){ this.y = y; }

    public double getCenterX(){
        return x + width / 2;
    }

    public double getCenterY(){
        return y + height / 2;
    }

   
    public void update(InputHandler input){

        isMoving = false;

        double nextX = x;
        double nextY = y;

        if(input.isPressed(KeyCode.W)){
            nextY -= speed;
            isMoving = true;
        }

        if(input.isPressed(KeyCode.S)){
            nextY += speed;
            isMoving = true;
        }

        if(input.isPressed(KeyCode.A)){
            nextX -= speed;
            isMoving = true;
            facingRight = false;
        }

        if(input.isPressed(KeyCode.D)){
            nextX += speed;
            isMoving = true;
            facingRight = true;
        }

      
        if(!isBlocked(nextX, y)){
            x = nextX;
        }

        if(!isBlocked(x, nextY)){
            y = nextY;
        }

        if(isMoving){
            current = walk;
        } else {
            current = idle;
        }

        current.update();
    }

  
    private boolean isBlocked(double x, double y){

        double left = x;
        double right = x + width;
        double top = y;
        double bottom = y + height;

        return map.isBlocked((int)left, (int)top) ||
               map.isBlocked((int)right, (int)top) ||
               map.isBlocked ((int)left, (int)bottom) ||
               map.isBlocked((int)right, (int)bottom);
    }

    public void draw(GraphicsContext gc){
        gc.setImageSmoothing(false);

if(facingRight){
Image frame = current.getCurrentFrame();
if (frame != null) {
    gc.drawImage(frame, (int)x, (int)y, width, height);
} else {
    gc.setFill(javafx.scene.paint.Color.GREEN);
    gc.fillRect(x, y, width, height);
}
} else {
Image frame = current.getCurrentFrame();
if (frame != null) {
    gc.drawImage(frame, (int)(x + width), (int)y, -width, height);
} else {
    gc.setFill(javafx.scene.paint.Color.GREEN);
    gc.save();
    gc.translate(x + width, y);
    gc.scale(-1, 1);
    gc.fillRect(0, 0, width, height);
    gc.restore();
}
}
    }

    public void drawHealthBar(GraphicsContext gc) {
        int barWidth = 200;
        int barHeight = 20;

        int x = 20;
        int y = 20;

        gc.setFill(Color.RED);
        gc.fillRect(x, y, barWidth, barHeight);

        gc.setFill(Color.GREEN);
        double healthWidth = (double) currentHealth / maxHealth * barWidth;
        gc.fillRect(x, y, healthWidth, barHeight);
    }
}