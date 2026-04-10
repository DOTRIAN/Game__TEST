package main;


import core.Game;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Load FXML UI overlay - FIXED PATH
       
   

    StackPane root = new StackPane();
    root.getChildren().add(canvas);
    // UI buttons TOP layer (overlay)
        
        // Resize UI to fit scene - REMOVED setPrefSize (Parent undefined), keep autosize
        
        Scene scene = new Scene(root, 800, 600);
        
        stage.setTitle("2D Game with UI Buttons - FIXED");
        stage.setScene(scene);
        stage.show();
        
        canvas.requestFocus();  // Game input focus
        
        Game game = new Game(gc, scene);
       
        game.start();
        
    }
    
    public static void main(String[] args) {
        launch();
    }
}
