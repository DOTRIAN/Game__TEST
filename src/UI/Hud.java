package ui;

import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import  javafx.scene.paint.Color;



public class Hud {

    public void render(GraphicsContext graphicsContext, Player player){
        graphicsContext.setFill((Color.BLACK));
        graphicsContext.fillText("PLayer X: "+ player.getX(),20,110);
        graphicsContext.fillText("Player Y: " + player.getY(),20,135);
        graphicsContext.fillText("HP: "+player.getHp()+ " / " + player.getMaxHp(),20,160);
    }


}
