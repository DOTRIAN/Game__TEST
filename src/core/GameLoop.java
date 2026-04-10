package core;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;


    public class GameLoop extends AnimationTimer {

        private Game game;

        public GameLoop(Game game) {
            this.game = game;
        }

@Override
    public void handle(long now) {
        
        game.update();
        game.render();
        
    }
    }

