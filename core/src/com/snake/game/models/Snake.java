package com.snake.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.LinkedList;

import com.snake.game.game.AssetController;
import com.snake.game.game.GameState;
import com.snake.game.models.items.Cell;

import static com.snake.game.game.GameState.SCALE;


public class Snake {

    private static final int INITIAL_BODY_COUNT = 3;

    private TextureAtlas atlas;

    private LinkedList<Cell> body;
    private Cell head;
    private Cell tail;

    private Boolean alive;
    private Moves dir;


    public Snake(TextureAtlas atlas) {
        this.dir = Moves.RIGHT;
        this.atlas = atlas;
        body = new LinkedList<Cell>();
        alive = true;
        init();
    }

    public void revive() {
        alive = true;
    }

    public void init() {
        body.clear();
        for (int i = INITIAL_BODY_COUNT; i > 0; i--) {
            Cell body = new Cell(AssetController.instance().getSprite(getBodyType(i)), SCALE * i, 0);
            this.body.add(body);
        }
        dir = Moves.RIGHT;
        head = body.getFirst().setOriginCenter();
        tail = body.getLast().setOriginCenter();
    }

    private String getBodyType(int index) {
        if (index == INITIAL_BODY_COUNT) return "snake_head";
        if (index == 1)                  return "snake_tail";
        else                             return "snake_body";
    }

    public void move() { // Moves snake in current direction
        for (int i = body.size() - 1; i > 0; i--) {
            Cell nextBodyPart = body.get(i - 1);
            Cell currentBodyPart = body.get(i);
            currentBodyPart.setPosition(nextBodyPart.getX(), nextBodyPart.getY());
        }
        head.setDirection(dir);
        checkWallCollision();
    }

    public void handleInput() {
             if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && dir != Moves.DOWN)    dir = Moves.UP;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && dir != Moves.UP)    dir = Moves.DOWN;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && dir != Moves.RIGHT) dir = Moves.LEFT;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && dir != Moves.LEFT) dir = Moves.RIGHT;
    }


    public void render(SpriteBatch batch) {
        for (Cell body : body) {
            body.draw(batch);
        }
    }

    public boolean isCrash() {
        for (int i = 1; i < body.size(); i++) {
            if (head.isCollide(body.get(i))) return true;
        }
        return false;
    }

    private void checkWallCollision() {
        if (head.getY() > GameState.SCREEN_HEIGHT)  head.setY(0);
        if (head.getY() < 0)                        head.setY(GameState.SCREEN_HEIGHT);
        if (head.getX() > GameState.SCREEN_WIDTH)   head.setX(0);
        if (head.getX() < 0)                        head.setX(GameState.SCREEN_WIDTH);
    }


    public boolean isFoodTouch(Item food) {
        return this.body.getFirst().isCollide(food);
    }

    public void grow() {
        body.getLast().sprite.setRegion(atlas.getRegions().get(1));
        Cell body = new Cell(atlas.createSprite("snake_tail"), tail.getX(), tail.getY());
        this.body.add(body);
        tail = this.body.getLast().setOriginCenter();
        System.out.println(this.body.size());
    }

    public boolean isAlive() {
        return alive;
    }

    public LinkedList<Cell> getBody() {
        return body;
    }

    public void die() {
        alive = false;
    }

    public void reset() {
        init();
    }
}
