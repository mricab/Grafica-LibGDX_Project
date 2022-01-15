package com.snake.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.snake.game.game.Input.IInputController;
import com.snake.game.game.Input.InputController;
import com.snake.game.game.Input.InputEvent;
import com.snake.game.models.Board;
import com.snake.game.primitives.Direction;
import com.snake.game.models.Snake;
import com.snake.game.primitives.Level;
import com.snake.game.primitives.MoveResponse;
import com.snake.game.primitives.Position;
import com.snake.game.utils.Writer;

import static com.snake.game.game.GameState.LEVEL_THRESHOLD;


public class GameController extends Thread implements IInputController  {

    private static final int WIDTH = Gdx.graphics.getWidth();
    private static final int HEIGHT = Gdx.graphics.getHeight();

    private Snake snake;
    private Board board;

    private boolean isGameStart;
    private boolean isGameOver;
    private MoveResponse response;
    private Level currentLevel;

    private boolean run = true;
    private long lastRun;
    private long accumulatedTime;
//    private float accumulatedTime;
    private long speed;

    private Writer writer;

    private boolean keyPressed = false;

    public GameController(InputController ic) {

        ic.addListener(this);

        snake = new Snake(ic);

        isGameStart = true;
        isGameOver = false;

        accumulatedTime = 0;
        writer = new Writer(Color.BLACK);

        currentLevel = Level.lvl0;
        init();
    }

    public void cancel() {
        this.run = false;
    }

    public void run() {
        System.out.println("Game Controller running");

        while(run) {

            try {
                if (isGameStart) { // GameStart

                    if (keyPressed) {
                        keyPressed = false;
                        lastRun = System.currentTimeMillis();
                        startGame();
                    }

                } else {

                    if (snake.isAlive()) { // Playing
                        long now = System.currentTimeMillis();
                        accumulatedTime += now - lastRun;
                        lastRun = now;

                        if (accumulatedTime >= speed) {
                            response = board.moveSnake(Direction.RIGHT);
                            accumulatedTime = 0;

                            if (response == MoveResponse.CRASHED) {
                                snake.die();
                                if(Snake.lives() == 0) currentLevel = Level.lvl0;
                                else init();
                            }

                            if (response == MoveResponse.EATEN) {
                                ScoreManager.score();
                            }
                        }

                        if (ScoreManager.getScore() >= LEVEL_THRESHOLD) {
                            currentLevel = currentLevel.next();
                            init();
                        }

                    } else { // GameOver

                        isGameOver = true;
                        if (keyPressed) {
                            keyPressed = false;
                            snake.live();
                            init();
                        }
                    }
                }

                Thread.sleep(10);

            } catch (Exception e) {

                break;
            }
        } // while
    }

    private void startGame() {
        isGameStart = false;
        snake.live();
    }

    private void init() {
        board = MapManager.instance().LoadMap("maps/" + currentLevel.toString() + ".txt", snake);
        board.init();

        isGameOver = false;
        speed = currentLevel.speed;
        ScoreManager.reset();
    }

    public void render(SpriteBatch batch) {

        Writer.Color(Color.WHITE);
        Position middle = new Position(WIDTH/2, HEIGHT/2);

        if (isGameStart) {
            // Start Message
            Writer.Draw(batch, "WELCOME", new Position(middle.X - 100, middle.Y));
            Writer.Draw(batch, "Press ENTER to start playing", new Position(middle.X - 200, middle.Y + 30));
        } else if (isGameOver) {
            // GameOver Message
            Writer.Draw(batch, "GAME OVER",  new Position(middle.X - 100, middle.Y));
            Writer.Draw(batch, "Press ENTER to start playing", new Position(middle.X - 200, middle.Y + 30));
        } else {
            // Game Screen
            board.render(batch);
            Writer.Draw(batch, "Score: " + ScoreManager.getScore(), new Position(10,10));
            Writer.Draw(batch, "Lives: " + Snake.lives(), new Position(10,30));
        }
    }

    @Override
    public void OnInputReceived(InputEvent e) {
        if(e.key == 66) keyPressed = true;
        if(e.key == 111) cancel();
    }
}
