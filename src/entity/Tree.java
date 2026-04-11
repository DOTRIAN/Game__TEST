package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Tree {
    private double x;
    private double y;
    private double width;
    private double height;

    public Tree(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public double getX() {
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

    public void draw(GraphicsContext graphicsContext, double cameraX, double cameraY) {
        double screenX = x - cameraX;  // do x,y là tọa độ của world
        double screenY = y - cameraY;  // nên muốn vẽ lên màn hình thì phải trừ camera

        graphicsContext.setFill(Color.SADDLEBROWN);
        graphicsContext.fillRect(screenX + 24, screenY + 36, 16, 28);

        graphicsContext.setFill(Color.DARKGREEN);
        graphicsContext.fillOval(screenX, screenY, width, height - 12);
    }  // draw tree


}