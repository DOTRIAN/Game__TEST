package ui;

import core.GameState;
import entity.Player;
import entity.Enemy;
import input.InputHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import  core.GameConfig;

public class Renderer {



    private final Canvas canvas;  // Là vùng để vẽ game
    private final GraphicsContext graphicsContext;   // là cây bút để vẽ canvas
    private final Hud hud;

    public Renderer(Stage stage, InputHandler inputHandler) {
        this.canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.hud= new Hud();


        StackPane root = new StackPane(canvas);// tạo 1 StackPane và đặt canvas trong nó
        // StackPane là 1 layout container của JVFX để quản lý các node con
        // ko đưa canvas vào thẳng scene vì scene thường nhận 1 root node, và StackPane là root đó

        Scene scene = new Scene(root, GameConfig.WIDTH,GameConfig.HEIGHT);
        inputHandler.attach(scene);
        //gán input vào scene, kiểu hãy đọc input trên scene này

        stage.setScene(scene); // đặt scene làm nội dung của cửa sổ stage
        //->> kể từ giờ cửa sổ sẽ hiển thị scene vừa tạo
        stage.show();
    }

    public void render(GameState gameState, Player player,Enemy enemy) {
        graphicsContext.setFill(Color.BEIGE);
        graphicsContext.fillRect(0, 0,GameConfig.WIDTH, GameConfig.HEIGHT);

        graphicsContext.setFill(Color.DARKGREEN);
        graphicsContext.fillText("State: " + gameState, 20, 30);
        graphicsContext.fillText("Skeleton render running", 20, 55);

        graphicsContext.setFill((Color.DODGERBLUE));
        graphicsContext.fillRect(
                player.getX(),player.getY(),
                player.getWidth(),player.getHeight()

        );

        graphicsContext.setFill(Color.CRIMSON);
        graphicsContext.fillRect(
                enemy.getX(),enemy.getY(),
                enemy.getWidth(),enemy.getHeight()
        );
        hud.render(graphicsContext,player);

        if (gameState == GameState.GAME_OVER){
            graphicsContext.setFill(Color.DARKRED);
            graphicsContext.fillText(("Game OVER"),400,270);
        }
    }
}
