package entity;

import java.util.ArrayList;
import java.util.List;
import entity.NPC;
import javafx.scene.canvas.GraphicsContext;

public class EntityManager {
      private List<NPC> npcs;
      public EntityManager(){
        npcs= new ArrayList<>();
      }
      public void addEntity(NPC npc){
        npcs.add(npc);
      }
      public void update(){
        for(NPC npc:npcs){
            npc.update();
        }
      }
      public void draw(GraphicsContext gc){
        for(NPC npc:npcs){
            npc.draw(gc);
        }
      }
      public NPC get(int index){
        return npcs.get(index);
      }
      
}
