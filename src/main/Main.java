package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        stage.setTitle("Tribe Survival Game");
        stage.setScene(scene);
        stage.show();

        // Game Loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw(gc);
            }
        }.start();
    }

    public void update() {
        // logic game (để trống trước)
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 600);

        // test vẽ
        gc.fillText("Game Start", 350, 300);
    }

    public static void main(String[] args) {
        launch();
    }
}