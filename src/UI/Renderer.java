package UI;

import core.Gamestate;
import entity.Player;
import entity.Wolf;
import entity.Tree;
import input.InputHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import core.GameConfig;
import javafx.scene.image.Image;
import java.util.List;

public class Renderer {

    private final Canvas canvas;
    private final GraphicsContext graphicsContext;

    private final Image[] wolfRunFrames;
    private int wolfFrameIndex;
    private long lastWolfFrameTime;
    private static final long WOLF_FRAME_DURATION_NS = 120_000_000L;
    private final Image backgroundImage;

    public Renderer(Stage stage, InputHandler inputHandler) {
        this.canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.wolfRunFrames = new Image[]{
                new Image("file:assets/wolf_run/wolf_run_0001.png"),
                new Image("file:assets/wolf_run/wolf_run_0002.png"),
                new Image("file:assets/wolf_run/wolf_run_0003.png"),
                new Image("file:assets/wolf_run/wolf_run_0004.png"),
                new Image("file:assets/wolf_run/wolf_run_0005.png"),
                new Image("file:assets/wolf_run/wolf_run_0006.png")
        };
        this.wolfFrameIndex = 0;
        this.lastWolfFrameTime = 0;
        this.backgroundImage = new Image("file:assets/backgrounds/grass03.png");

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, GameConfig.WIDTH,GameConfig.HEIGHT);
        inputHandler.attach(scene);
        stage.setScene(scene);
        stage.show();
    }

    public void render(Gamestate gameState, Player player, Wolf wolf,long now,boolean wolfMoving, List<Tree> trees,double cameraX, double cameraY) {
        graphicsContext.clearRect(0, 0, (int)GameConfig.WIDTH, (int)GameConfig.HEIGHT);

        if (backgroundImage.isError()) {
            graphicsContext.setFill(Color.BEIGE);
            graphicsContext.fillRect(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
        } else {
            graphicsContext.drawImage(backgroundImage, 0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
        }

        for (Tree tree : trees) {
            double treeScreenX = tree.getX() - cameraX;
            double treeScreenY = tree.getY() - cameraY;
            if (treeScreenX + tree.getWidth() < 0 || treeScreenX > GameConfig.WIDTH ||
                treeScreenY + tree.getHeight() < 0 || treeScreenY > GameConfig.HEIGHT) continue;
            graphicsContext.setFill(Color.SADDLEBROWN);
            graphicsContext.fillRect(treeScreenX + 24, treeScreenY + 36, 16, 28);
            graphicsContext.setFill(Color.DARKGREEN);
            graphicsContext.fillOval(treeScreenX, treeScreenY, tree.getWidth(), tree.getHeight() - 12);
        }

        graphicsContext.setFill(Color.DARKGREEN);
        graphicsContext.fillText("State: " + gameState, 20, 30);
        graphicsContext.fillText("WASD: move", 20, 55);
        graphicsContext.fillText("J: take damage | K: heal", 20, 80);

        graphicsContext.setFill(Color.DODGERBLUE);
        double playerScreenX = player.getX() - cameraX;
        double playerScreenY = player.getY() - cameraY;
        graphicsContext.fillRect(playerScreenX, playerScreenY, player.getWidth(), player.getHeight());

        if (!wolfMoving){
            wolfFrameIndex = 0;
        } else if (wolfMoving && now - lastWolfFrameTime >= WOLF_FRAME_DURATION_NS) {
            wolfFrameIndex = (wolfFrameIndex + 1) % wolfRunFrames.length;
            lastWolfFrameTime = now;
        }

        boolean facingRight = (player.getX() + player.getWidth() / 2) >= (wolf.getX() + wolf.getWidth() / 2);
        Image currentWolfFrame = wolfRunFrames[wolfFrameIndex];
        double wolfScreenX = wolf.getX() - cameraX;
        double wolfScreenY = wolf.getY() - cameraY;
        if (currentWolfFrame.isError()) {
            graphicsContext.setFill(Color.CRIMSON);
            graphicsContext.fillRect(wolfScreenX, wolfScreenY, wolf.getWidth(), wolf.getHeight());
        } else {
            if (facingRight) {
                graphicsContext.drawImage(currentWolfFrame, wolfScreenX, wolfScreenY, wolf.getWidth(), wolf.getHeight());
            } else {
                graphicsContext.save();
                graphicsContext.translate(wolfScreenX + wolf.getWidth(), wolfScreenY);
                graphicsContext.scale(-1, 1);
                graphicsContext.drawImage(currentWolfFrame, 0, 0, wolf.getWidth(), wolf.getHeight());
                graphicsContext.restore();
            }
        }

        // HUD
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText("Player X: " + player.getX(), 20, 110);
        graphicsContext.fillText("Player Y: " + player.getY(), 20, 135);
        graphicsContext.fillText("HP: " + player.getHp() + " / " + player.getMaxHp(), 20, 160);

        if (gameState == Gamestate.GAME_OVER) {
            graphicsContext.setFill(new Color(0.5, 0, 0, 0.8));
            graphicsContext.fillRect(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText("GAME OVER", 400, 250);
            graphicsContext.fillText("Wolf caught you. Press R to restart.", 320, 280);
        }
    }
}

