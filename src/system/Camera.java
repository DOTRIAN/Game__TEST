package system;

import entity.Player;

public class Camera {
    private double x, y;
    
    public void follow(double playerCenterX, double playerCenterY) {
        x = playerCenterX - 400; // screen/2
        y = playerCenterY - 300;
        
        if (x < 0) x = 0;
        if (y < 0) y = 0;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
}

