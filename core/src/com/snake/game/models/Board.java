package com.snake.game.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import com.snake.game.game.AssetController;
import com.snake.game.game.GameState;
import com.snake.game.models.items.Cell;

import static com.snake.game.game.GameState.SCALE;

public class Board {

    private Cell[][] cells;
    private Snake snake;
    public Item food;
    private String[] foodTypes = { "food_1", "food_2", "food_3" };
    private String[] obstacleTypes = { "obstacle_1", "obstacle_2", "obstacle_3" };


    public Board(Snake snake, int width, int height) {
        this.snake = snake;
        initBoard(width, height);
    }

    private void initBoard(int width, int height) {
        cells = new Cell[width / SCALE][height / SCALE];
        for (int rowGrass = 0; rowGrass < cells.length; rowGrass++) {
            for (int colGrass = 0; colGrass < cells[rowGrass].length; colGrass++) {
                Cell cell = new Cell(AssetController.instance().getSprite(randomGrass(rowGrass, colGrass)), rowGrass * SCALE, colGrass * SCALE);
                cells[rowGrass][colGrass] = cell;
            }
        }
    }

    private String randomGrass(int row, int col) {
        if (col % 2 == 0) {
            if (row % 2 != 0) return "grass_1";
            if (row % 2 == 0) return "grass_2";
        } else if (col % 2 != 0) {
            if (row % 2 != 0) return "grass_2";
            if (row % 2 == 0) return "grass_1";
        }
        return "grass_2";
    }

    public void render(SpriteBatch batch) {
        // Background
        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                aCell.draw(batch);
            }
        }
        // Food
        food.draw(batch);
    }

    public void generateFood() {
        int foodType = MathUtils.random(foodTypes.length - 1);
        Item newFood = new Item(AssetController.instance().getSprite(foodTypes[foodType]));
        newFood.setPosition(randX(), randY());

        for (Cell body : snake.getBody()) {
            while (newFood.getX() == body.getX() && body.getY() == newFood.getY()) {
                newFood.setPosition(randX(), randY());
            }
        }
        food = newFood;
    }

    private float randX() {
        return MathUtils.random(1, GameState.BOARD_WIDTH - 1) * SCALE;
    }

    private float randY() {
        return MathUtils.random(1, GameState.BOARD_HEIGHT - 1) * SCALE;
    }

    public void reset() {
        generateFood();
    }
}
