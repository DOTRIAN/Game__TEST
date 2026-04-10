package system;

import entity.Player;

public class BoundarySystem {

    private double mapWidth;
    private double mapHeight;

    public BoundarySystem(double mapWidth, double mapHeight){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }
    

    public void update(Player player){

        
        if(player.getX() < 0){
            player.setX(0);
        }

        if(player.getX() + player.getWidth() > mapWidth){
            player.setX(mapWidth - player.getWidth());
        }

      
        if(player.getY() < 0){
            player.setY(0);
        }

        if(player.getY() + player.getHeight() > mapHeight){
            player.setY(mapHeight - player.getHeight());
        }
    }
}