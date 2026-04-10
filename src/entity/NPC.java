package entity;

import javafx.scene.canvas.GraphicsContext;
import input.InputHandler;
import Map_Game.GameMap;
import entity.Player;
import entity.Animation;

public class NPC {
    private double x, y, width, height;
    private double speed1, speed2;
    private Player player;
    private GameMap map;
    private Animation animation; // optional animation
    
    public NPC(double x, double y, double width, double height, double speed1, double speed2, Player player, GameMap map) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed1 = speed1;
        this.speed2 = speed2;
        this.player = player;
        this.map = map;
        // dummy animation if needed
        try {
            animation = new Animation("idle.png", 64, 64, 10);
        } catch (Exception e) {
            animation = null;
        }
    }
    
    public void update() {
        // Simple AI move toward player
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        double dist = Math.sqrt(dx*dx + dy*dy);
        if (dist > 0) {
            x += (dx / dist) * speed1;
            y += (dy / dist) * speed2;
        }
        if (animation != null) animation.update();
    }
    
    public void draw(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLUE);
        gc.fillRect(x, y, width, height);
        
        if (animation != null && animation.getCurrentFrame() != null) {
            gc.drawImage(animation.getCurrentFrame(), x, y, width, height);
        }
    }
}

