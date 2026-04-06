package core;

import entity.Player;
import entity.Wolf;
import input.InputHandler;
import javafx.scene.input.KeyCode; //KeyCode là enum của JavaFX đại diện cho các phím trên bàn phím, VD: Keycode.W= W
import javafx.stage.Stage;
import ui.Renderer;
import entity.Tree;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private final GameLoop gameLoop;
    private final Renderer renderer;
    private final InputHandler inputHandler;
    private final Player player;
    private final Wolf wolf;
    private final List<Tree> trees; // 1 danh sach cay
    private GameState gameState;// tt của game, ví dụ: đang chơi, thua, tạm dừng,...
    private static final double PLAYER_START_X = 100; //toa do ban dau cua player
    private static final double PLAYER_START_Y = 100;
    private static final double ENEMY_START_X = 700;
    private static final double ENEMY_START_Y = 300;
    private static final long DAMAGE_COOLDOWN_NS = 500_000_000L;
    private static final double TREE_START_X = 300;
    private static final double TREE_START_Y = 220;
    //500_000_000L=  500 triệu nanoseconds = 0.5 giây
    private long lastDamageTime;
    private boolean wolfMoving; // xem wolf co dang di chuyen khong
    private double cameraX;
    private double cameraY;

    public Game(Stage stage) {
        this.inputHandler = new InputHandler();
        this.player = new Player(PLAYER_START_X,PLAYER_START_Y,32,32,4,100);
        this.renderer = new Renderer(stage, inputHandler);
        this.gameLoop = new GameLoop(this);// trruyeenf tham số là object Game hiện tại cho GameLoop, do đó
        //...GameLoop có thể gọi ngược lại game.update() và game.render().
        this.gameState = GameState.PLAYING;
        this.wolf = new Wolf(1200, 500,64,64,1);
        this.lastDamageTime = 0;
        this.trees = new ArrayList<>();
        trees.add(new Tree(300, 220, 64, 64));
        trees.add(new Tree(450, 150, 64, 64));
        trees.add(new Tree(600, 320, 64, 64));
        //cay trong screen ban dau
        trees.add(new Tree(300, 220, 64, 64));
        trees.add(new Tree(900, 300, 64, 64));
        trees.add(new Tree(1400, 700, 64, 64));
        this.cameraX = 0;
        this.cameraY = 0;

    }

    public void start() {
        gameLoop.start();
    } // gọi start của GameLoop

    public void update(long now) {
        if (gameState == GameState.GAME_OVER){
            if (inputHandler.isPressed((KeyCode.R))){
                restartGame();
            }
            return;
        }
        if (gameState != GameState.PLAYING) {
            return;
        } // nếu game ko ở state Playing thì dưng update

        inputHandler.update();
        double oldPlayerX = player.getX();
        double oldPlayerY = player.getY(); // lưu vị trí cũ của player
        if (inputHandler.isPressed(KeyCode.A)){
            player.moveLeft();
        }

        if (inputHandler.isPressed(KeyCode.D)){
            player.moveRight();
        }

        if (inputHandler.isPressed(KeyCode.W)){
            player.moveUp();
        }

        if (inputHandler.isPressed(KeyCode.S)){
            player.moveDown();
        }
        if (inputHandler.isPressed((KeyCode.J))){
            player.takeDamage((1));
        }
        if (inputHandler.isPressed((KeyCode.K))){
            player.heal(1);
        }
        player.clampPosition(0,0,GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        // set tọa độ ko vượ quá màn hình

        double oldWolfX = wolf.getX(); // lưu vị trí cũ của wolf để so sánh xem có đg dchuyen ko
        double oldWolfY = wolf.getY();


        wolf.moveToward(player);
        wolf.clampPosition(0,0,GameConfig.WIDTH,GameConfig.HEIGHT);
        if (isPlayerCollidingWithAnyTree()) {
            player.setPosition(oldPlayerX, oldPlayerY);
        } // nếu va chạm với cây thì quay lại vị trí cũ

        wolfMoving = oldWolfX != wolf.getX() || oldWolfY != wolf.getY();
        // neeus x,y thay doi thi nghia la wold is moving

        if (isColliding() && now - lastDamageTime >= DAMAGE_COOLDOWN_NS){
            player.takeDamage(1);
            // now - lastDamageTime tgian troi qua ke tu lan bi dame trc
            lastDamageTime = now;
            /*now là:
              thời gian hiện tại tính bằng nanoseconds
               do AnimationTimer cung cấp*/
        }

        if (!player.isAlive()){
            gameState = GameState.GAME_OVER;
        }
        updateCamera();

    }
    private void updateCamera() {
        cameraX = player.getX() + player.getWidth() / 2 - GameConfig.WIDTH / 2;
        cameraY = player.getY() + player.getHeight() / 2 - GameConfig.HEIGHT / 2;

        if (cameraX < 0) {
            cameraX = 0;
        }

        if (cameraY < 0) {
            cameraY = 0;
        }

        double maxCameraX = GameConfig.WORLD_WIDTH - GameConfig.WIDTH;
        double maxCameraY = GameConfig.WORLD_HEIGHT - GameConfig.HEIGHT;

        if (cameraX > maxCameraX) {
            cameraX = maxCameraX;
        }

        if (cameraY > maxCameraY) {
            cameraY = maxCameraY;
        }
    }
    //36 vao giai thich


        public void render(long now) {
        renderer.render(gameState, player, wolf,now,wolfMoving,trees,cameraX,cameraY);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getPlayer() {
         return player;
    }

    private boolean isColliding(){
        return player.getX() < wolf.getX() + wolf.getWidth()
                && player.getX() + player.getWidth() > wolf.getX()
                && player.getY() < wolf.getY() + wolf.getHeight()
                && player.getY() + player.getHeight() > wolf.getY();
    }
    private void restartGame(){
        player.reset(PLAYER_START_X, PLAYER_START_Y);
        wolf.reset(ENEMY_START_X,ENEMY_START_Y);
        gameState = GameState.PLAYING;
        lastDamageTime = 0;
        wolfMoving = false;// khi restart game coi nhu chua co chuyen dong
        cameraX = 0;
        cameraY = 0;
    }
    public boolean isWolfMoving() {
        return wolfMoving;
    }
    //do wolfMoving la private nen Renderer phai lay qua methods nay

    public List<Tree> getTrees(){
        return trees;
    }
    private boolean isPlayerCollidingWithAnyTree() {
        for (Tree tree : trees) {
            boolean colliding = player.getX() < tree.getX() + tree.getWidth()
                    && player.getX() + player.getWidth() > tree.getX()
                    && player.getY() < tree.getY() + tree.getHeight()
                    && player.getY() + player.getHeight() > tree.getY();

            if (colliding) {
                return true;
            }
        }

        return false;
    } // duyeejt từng cây, va chạm cây nào thì return true
}
