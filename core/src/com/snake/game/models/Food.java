package com.snake.game.models;

import com.badlogic.gdx.math.MathUtils;
import com.snake.game.primitives.CellType;
import com.snake.game.primitives.Direction;
import com.snake.game.primitives.Position;

public class Food {

    static public Cell generate(com.snake.game.primitives.Position max) {

        com.snake.game.primitives.CellType type = com.snake.game.primitives.CellType.values()[MathUtils.random(com.snake.game.primitives.CellType.FOOD_1.ordinal(), CellType.FOOD_3.ordinal())];
        com.snake.game.primitives.Position pos = Position.random(max);
        com.snake.game.primitives.Direction dir = Direction.RIGHT;

        return new Cell(pos, type, dir);
    }
}
