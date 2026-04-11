package ui;

import core.GameState;
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
import  core.GameConfig;
import javafx.scene.image.Image;
import java.util.List;
import javafx.scene.image.Image;

public class Renderer {



    private final Canvas canvas;  // Là vùng để vẽ game
    private final GraphicsContext graphicsContext;   // là cây bút để vẽ canvas
    private final Hud hud;
    private final Image backgroundImage;


    public Renderer(Stage stage, InputHandler inputHandler) {
        this.canvas = new Canvas(GameConfig.WIDTH, GameConfig.HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.hud= new Hud();
        this.backgroundImage = new Image("file:assets/backgrounds/grass03.png");


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

    public void render(GameState gameState, Player player, Wolf wolf,long now,boolean wolfMoving, List<Tree> trees,double cameraX, double cameraY) {
        if (backgroundImage.isError()) {
            graphicsContext.setFill(Color.BEIGE);
            graphicsContext.fillRect(0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);// neeus backround load lỗi s hiển thị nền này

        } else {
            graphicsContext.drawImage(
                    backgroundImage,
                    0, 0,
                    GameConfig.WIDTH, GameConfig.HEIGHT
            );
        }

        for (Tree tree : trees) {
            tree.draw(graphicsContext,cameraX,cameraY);  // tu goij de ve cay
        }
        //4 dongf treen la ve cay
        graphicsContext.setFill(Color.DARKGREEN);
        graphicsContext.fillText("State: " + gameState, 20, 30);
        graphicsContext.fillText("WASD: move", 20, 55);
        graphicsContext.fillText("J: take damage | K: heal", 20, 80);

        player.draw(graphicsContext,cameraX,cameraY);

        wolf.draw(graphicsContext,cameraX,cameraY,now,wolfMoving,player);

        hud.render(graphicsContext,player);

        if (gameState == GameState.GAME_OVER) {
            graphicsContext.setFill(Color.DARKRED);
            graphicsContext.fillText("GAME OVER", 400, 250);

            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillText("The wolf caught you.", 380, 280);
            graphicsContext.fillText("Press R to restart.", 385, 310);
        }
    }
}
