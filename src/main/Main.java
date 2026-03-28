package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;
import core.Game;

public class Main extends Application {
    //Application là 1 class của JAVA, kế thừa nó như tbao hãy hoạt động theo javafx

    @Override
    public void start(Stage stage) {
        Game game = new Game(stage);
        game.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
