package com.snake.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snake.game.primitives.CellType;
import com.snake.game.primitives.Direction;
import com.snake.game.primitives.MoveResponse;
import com.snake.game.primitives.Position;


public class Board {

    private Cell[][] cells;
    private Position size;
    private Snake snake;
//    private Cell snakeHead;
//    private Cell snakeTail;

    public Board(int rows, int cols, Snake snake) {
        this.cells = new Cell[rows][cols];
        this.size = new Position(cols, rows);
        this.snake = snake;
    }

    public void init() {
        generateFood();
    }

    public void setCell(CellType type, Direction dir, Position pos) throws Exception {
        if (pos.Y < 0 || pos.Y >= size.Y) throw new Exception("Invalid row index.");
        if (pos.X < 0 || pos.X >= size.X) throw new Exception("Invalid col index.");

        Cell newCell = new Cell(pos, type, dir);
        cells[pos.Y][pos.X] = newCell;

        switch (newCell.type) {
            case SNAKE_HEAD: snake.head = newCell; break;
            case SNAKE_TAIL: snake.tail = newCell; break;
            default: break;
        }
    }

    public void render(SpriteBatch batch) {
        for (Cell[] data: cells) {
            for (Cell cell: data ) {
                cell.draw(batch);
            }
        }
    }

    public MoveResponse moveSnake(Direction dir) {

        boolean eating = false;

        Position oldHeadPos = new Position(snake.head.pos); Direction oldHeadDir = snake.head.dir;
        Position oldTailPos = new Position(snake.tail.pos); Direction oldTailDir = snake.tail.dir;

        // check
        Position nextCellPos = oldHeadPos.next(oldHeadDir, size);
        Cell nextCell = cells[nextCellPos.Y][nextCellPos.X];
        if (isCollision(nextCell)) return MoveResponse.CRASHED;
        if (isNourishment(nextCell)) eating = true;

        // move
        try {
            // Replace old head with body
            setCell(CellType.SNAKE_BODY, oldHeadDir, oldHeadPos);

            // Create new head (with same dir)
            Position newHeadPos = new Position(oldHeadPos.move(oldHeadDir, size));
            setCell(CellType.SNAKE_HEAD, oldHeadDir, newHeadPos);

            // Replace old tail with grass.
            if(!eating) setCell(CellType.GRASS_1, Direction.RIGHT, oldTailPos);

            // Calculate new tail position and change its type to tail.
            if(!eating) {
                Position newTailPos = oldTailPos.move(oldTailDir, size);
                Direction lastBodyDir = cells[newTailPos.Y][newTailPos.X].dir;
                setCell(CellType.SNAKE_TAIL, lastBodyDir, newTailPos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(eating) {
            generateFood();
            return MoveResponse.EATEN;
        }
        else {
            return MoveResponse.NONE;
        }

    }

    public boolean isCollision(Cell nextCell) {
        if(nextCell.type == CellType.OBSTACLE
                || nextCell.type == CellType.SNAKE_BODY
                || nextCell.type == CellType.SNAKE_HEAD
                || nextCell.type == CellType.SNAKE_TAIL)
        {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNourishment(Cell nextCell) {
        if(nextCell.type == CellType.FOOD_1
                || nextCell.type == CellType.FOOD_2
                || nextCell.type == CellType.FOOD_3)
        {
            return true;
        } else {
            return false;
        }
    }

    public void generateFood() {
        Cell food = Food.generate(size);
        while (cells[food.pos.Y][food.pos.X].type != CellType.GRASS_1) {
            food = Food.generate(size);
        }
        cells[food.pos.Y][food.pos.X] = food;
    }
}
