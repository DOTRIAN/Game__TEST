package core;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import UI.Renderer;
import Map_Game.GameMap;
import input.InputHandler;
import entity.CreateEntity;
import entity.EntityManager;
import entity.NPC;
import entity.Player;
import system.BoundarySystem;
import system.Camera;

public class Game {
  
    private GameLoop gameLoop;
    private Renderer renderer;
    
    private BoundarySystem boundarySystem;
    private NPC npc;
    private boolean gameOver = false;
    private boolean paused =false;

    private Player player;
    private Camera camera;
    private GameMap gameMap; 
    private InputHandler input;
    private EntityManager entityManager;
    
    public void setPaused(boolean paused){
        this.paused=paused;
    }
    public boolean isPaused(){
        return paused;
    }
    
    public Game(GraphicsContext gc, Scene scene) {

        this.input = new InputHandler(scene);
        
        renderer = new Renderer(gc);

        gameMap = new GameMap(); 

gameMap.loadTileset("assets/48x48 resize/gentle forest (48x48 resize) v01.png");
gameMap.loadFromTMX("src/Map_Game/map2.tmx"); 
     
        player = new Player(200, 200, 96, 96, 5, gameMap); 
        camera = new Camera();

        gameLoop = new GameLoop(this);
        
        boundarySystem = new BoundarySystem(2304, 2304); 
        
        npc = new NPC(300, 300, 96, 96, 2, 2, player,gameMap);
        
        entityManager = new EntityManager();
        entityManager.addEntity(CreateEntity.createZombie(300, 400, player,gameMap)); 
        
        entityManager.addEntity(CreateEntity.createBoss(600, 200, player,gameMap)); 

    }

    public void start() {
        gameLoop.start();
    }

    public void update() {
        if(isPaused()) return;
        if (gameOver) return;

        player.update(input);
        
        if (player.getHealth() <= 0) {
            gameOver = true;
        }
        
npc.update();

        entityManager.update();
        boundarySystem.update(player);
        
        camera.follow(player.getCenterX(), player.getCenterY());
        
    }

   
    public void render() {    
        renderer.render(player, gameMap, camera, npc, entityManager,gameOver); // THAY ĐỔI: truyền gameMap thay vì map
        if (gameOver || isPaused()) {
            return;
        }
    }
}
