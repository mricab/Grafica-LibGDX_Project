package com.snake.game.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.snake.game.models.Item;
import com.snake.game.models.Moves;

import static com.snake.game.game.GameState.SCALE;

public class Cell extends Item {


    public Cell(Sprite sprite, float x, float y) {
        super(sprite, x, y);
        setSize(SCALE, SCALE);
    }

    public void setDirection(Moves dir) {
        if (dir == Moves.UP) {
            sprite.setRotation(90);
            setPosition(x, y + SCALE);
        } else if (dir == Moves.DOWN) {
            sprite.setRotation(-90);
            setPosition(x, y - SCALE);
        } else if (dir == Moves.LEFT) {
            sprite.setRotation(180);
            setPosition(x - SCALE, y);
        } else if (dir == Moves.RIGHT) {
            sprite.setRotation(0);
            setPosition(x + SCALE, y);
        }
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x= " + x +
                ", y= " + y +
                '}';
    }

    public Cell setOriginCenter() {
        sprite.setOriginCenter();
        return this;
    }
}
