package com.snake.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.snake.game.models.Board;
import com.snake.game.models.Snake;


public class GameController {

    private static final int WIDTH = Gdx.graphics.getWidth();
    private static final int HEIGHT = Gdx.graphics.getHeight();

    private Board board;
    private Snake snake;

    private boolean isGameStart;
    private boolean isGameOver;

    private float accumulatedTime;
    private float speed;
    private BitmapFont Writer;


    public GameController() {
        TextureAtlas atlas = AssetController.instance().get(AssetController.ELEMENTS_PACK);
        Writer = AssetController.instance().get(AssetController.PIXEL_FONT);
        Writer.setColor(Color.BLACK);
        snake = new Snake(atlas);
        board = new Board(snake, WIDTH, HEIGHT);
        board.generateFood();
        init();
    }

    private void init() {
        isGameStart = true;
        speed = 0.09f;
    }

    public void update(float delta) {
        if (isGameStart) { // GameStart
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) start();
        } else {
            if (snake.isAlive()) { // Playing
                accumulatedTime += delta;
                snake.handleInput();
                if (accumulatedTime >= speed) {
                    snake.move();
                    accumulatedTime = 0;
                }
                if (snake.isCrash()) {
                    snake.reset();
                    snake.die();
                }
                if (snake.isFoodTouch(board.food)) {
                    ScoreManager.score();
                    snake.grow();
                    board.generateFood();
                }
            } else { // GameOver
                isGameOver = true;
                if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) restart();
            }
        }
    }

    private void start() {
        isGameStart = false;
    }

    private void restart() {
        isGameOver = false;
        snake.reset();
        snake.revive();
        board.generateFood();
        ScoreManager.reset();
    }

    public void render(SpriteBatch batch) {

        // Game Screen
        board.render(batch);
        snake.render(batch);
        Writer.draw(batch,
                "Score: " + ScoreManager.getScore(),
                GameState.SCALE/2,
                GameState.SCREEN_HEIGHT - GameState.SCALE/4);

        // Start Screen
        if (isGameStart) {
            Writer.draw(batch,
                    "WELCOME",
                    (WIDTH - 100) / 2,
                    (HEIGHT + 100) / 2);
            Writer.draw(batch,
                    "Press any key to start playing",
                    (WIDTH - 250) / 2,
                    (HEIGHT + 50) / 2);
        }

        // GameOver Screen
        if (isGameOver) {
            Writer.draw(batch,
                    "GAME OVER",
                    (WIDTH - 100) / 2,
                    (HEIGHT + 100) / 2);
            Writer.draw(batch,
                    "Press any key to continue",
                    (WIDTH - 250) / 2,
                    (HEIGHT + 50) / 2);
        }
    }

}
