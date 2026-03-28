package core;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;

public class Game {
    private Canvas canvas;
    private GraphicsContext gc;
    // bút đê vẽ lên canvas

    public Game(Stage stage) {
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Game");
        stage.show();
    }

    public void start() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
            }
        }.start();
    }
    /* hàm handle dùng để render lại các frame trong mỗi lần chạy */

    private void update() {
        // logic game
    }

    private void draw() {
        gc.clearRect(0, 0, 800, 600);  // dùng để xóa đi frame cũ, tránh để lại bóng
    }
}
