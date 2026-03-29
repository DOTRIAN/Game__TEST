package core;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;

public class Game {

    private GameLoop gameLoop;
    private Renderer renderer;
    private InputHandler input;

    public Game(Stage stage) {
        renderer = new Renderer(stage);
        input = new InputHandler();
        gameLoop = new GameLoop(this);
    }

    public void start() {
        gameLoop.start();
    }

    public void update() {
        input.update();
    }

    public void render() {
        renderer.render();
    }
}
