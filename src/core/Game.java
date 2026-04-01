package core;

import entity.Player;
import input.InputHandler;
import javafx.scene.input.KeyCode; //KeyCode là enum của JavaFX đại diện cho các phím trên bàn phím, VD: Keycode.W= W
import javafx.stage.Stage;
import ui.Renderer;
import entity.Enemy;

public class Game {

    private final GameLoop gameLoop;
    private final Renderer renderer;
    private final InputHandler inputHandler;
    private final Player player;
    private final Enemy enemy;
    private GameState gameState;// tt của game, ví dụ: đang chơi, thua, tạm dừng,...
    private static final double PLAYER_START_X = 100; //toa do ban dau cua player
    private static final double PLAYER_START_Y = 100;
    private static final double ENEMY_START_X = 700;
    private static final double ENEMY_START_Y = 300;


    public Game(Stage stage) {
        this.inputHandler = new InputHandler();
        this.player = new Player(PLAYER_START_X,PLAYER_START_Y,32,32,4,100);
        this.renderer = new Renderer(stage, inputHandler);
        this.gameLoop = new GameLoop(this);// trruyeenf tham số là object Game hiện tại cho GameLoop, do đó
        //...GameLoop có thể gọi ngược lại game.update() và game.render().
        this.gameState = GameState.PLAYING;
        this.enemy = new Enemy(ENEMY_START_X, ENEMY_START_Y,32,32,2);

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
        player.clampPosition(0,0,GameConfig.WIDTH, GameConfig.HEIGHT);
        // set tọa độ ko vượ quá màn hình

        enemy.moveToward(player);
        enemy.clampPosition(0,0,GameConfig.WIDTH,GameConfig.HEIGHT);

        if (isColliding()){
            player.takeDamage(1);
        }
        if (!player.isAlive()){
            gameState = GameState.GAME_OVER;
        }


    }

    public void render() {
        renderer.render(gameState, player,enemy);
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
        return player.getX() < enemy.getX() + enemy.getWidth()
                && player.getX() + player.getWidth() > enemy.getX()
                && player.getY() < enemy.getY() + enemy.getHeight()
                && player.getY() + player.getHeight() > enemy.getY();
    }
    private void restartGame(){
        player.reset(PLAYER_START_X, PLAYER_START_Y);
        enemy.reset(ENEMY_START_X,ENEMY_START_Y);
        gameState = GameState.PLAYING;
    }
}
