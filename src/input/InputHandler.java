package input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

public class InputHandler {

    private Set<KeyCode> keysPressed = new HashSet<>();

    public InputHandler(Scene scene){
        scene.setOnKeyPressed(e -> keysPressed.add(e.getCode()));
        scene.setOnKeyReleased(e -> keysPressed.remove(e.getCode()));
    }

    public boolean isPressed(KeyCode key){
        return keysPressed.contains(key);
    }
}