package entity;

import Map_Game.GameMap;
import entity.Player;

public class CreateEntity {
    public static NPC createZombie(double x, double y, Player player, GameMap map) {
        return new NPC(x, y, 64, 64, 1, 1, player, map);
    }
    
    public static NPC createBoss(double x, double y, Player player, GameMap map) {
        return new NPC(x, y, 96, 96, 2, 2, player, map);
    }
}

