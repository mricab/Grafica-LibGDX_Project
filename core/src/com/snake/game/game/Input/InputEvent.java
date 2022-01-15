package com.snake.game.game.Input;

import java.util.EventObject;
import com.badlogic.gdx.Input;

public class InputEvent extends EventObject {

    public int key;

    public InputEvent(Object source, int key) {
        super(source);
        this.key = key;
    }
}
