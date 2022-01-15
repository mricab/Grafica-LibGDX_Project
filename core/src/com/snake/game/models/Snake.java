package com.snake.game.models;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.snake.game.game.Input.IInputController;
import com.snake.game.game.Input.InputController;
import com.snake.game.game.Input.InputEvent;
import com.snake.game.primitives.Direction;
import com.snake.game.primitives.Live;
import static com.snake.game.game.GameState.SNAKE_LIVES;

public class Snake implements IInputController {

    public Cell head;
    public Cell tail;

    static private Stack<Live> lives;

    public Snake(InputController ic) {
        this.lives = new Stack<Live>();
        ic.addListener(this);
    }

    public void live() {
        for (int i = 0; i < SNAKE_LIVES; i++) {
            lives.add(Live.ONE_LIVE);
        }
    }

    public void die() {
        lives.pop();
    }

    public boolean isAlive() {
        return !lives.isEmpty();
    }

    static public int lives() {
        return lives.size();
    }

    @Override
    public void OnInputReceived(InputEvent e) {
             if (e.key == 19 && head.dir != Direction.DOWN)  head.dir = Direction.UP;
        else if (e.key == 20 && head.dir != Direction.UP)    head.dir = Direction.DOWN;
        else if (e.key == 21 && head.dir != Direction.RIGHT) head.dir = Direction.LEFT;
        else if (e.key == 22 && head.dir != Direction.LEFT)  head.dir = Direction.RIGHT;
    }
}
