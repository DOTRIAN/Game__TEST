package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Map {
    private double x;
    private double y;
    private double width;
    private double height;
    private Image background;
    public Map(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        background = new Image("UI/MAT/BG.png");
    }

    public void update(){
        
    }

    public void draw(GraphicsContext gc){
        
        gc.drawImage(background,x, y, width, height); 
    }
}