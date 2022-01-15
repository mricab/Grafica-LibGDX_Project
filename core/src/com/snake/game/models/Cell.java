package com.snake.game.models;

import com.snake.game.handlers.AssetController;
import com.snake.game.primitives.CellType;
import com.snake.game.primitives.Direction;
import com.snake.game.primitives.Position;
import com.snake.game.handlers.DrawableSprite;

import static com.snake.game.game.GameState.CELL_SIZE;

public class Cell extends DrawableSprite {

    public com.snake.game.primitives.CellType type;
    public com.snake.game.primitives.Direction dir;
    public com.snake.game.primitives.Position pos;

    public Cell(Position pos, com.snake.game.primitives.CellType type, com.snake.game.primitives.Direction dir) {
        super(AssetController.instance().getSprite(type.toString()), pos.X * CELL_SIZE, pos.Y * CELL_SIZE);
        setSize(CELL_SIZE, CELL_SIZE);
        this.pos = pos;
        this.type = type;
        setDirection(dir);
    }

    public void setType(CellType type) {
        this.type = type;
        this.sprite = AssetController.instance().getSprite(type.toString());
    }

    public void setDirection(Direction dir) {
        this.dir = dir;

//             if (dir == Direction.UP)   { sprite.setRotation(-90);  }
//        else if (dir == Direction.DOWN) { sprite.setRotation(90); }
//        else if (dir == Direction.LEFT) { sprite.setRotation(180); }
//        else if (dir == Direction.RIGHT){ sprite.setRotation(0);   }
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
