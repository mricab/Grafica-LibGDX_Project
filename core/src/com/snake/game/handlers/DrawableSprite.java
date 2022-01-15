package com.snake.game.handlers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.snake.game.game.GameState;

public class DrawableSprite {

    protected Sprite sprite;
    protected float x;
    protected float y;

    public DrawableSprite(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        setPosition(x, y);
        init();
    }

    public DrawableSprite(Sprite sprite) {
        this.sprite = sprite;
        setSize(GameState.CELL_SIZE, GameState.CELL_SIZE);
    }

    private void init() {
        sprite.flip(false,true);
    }


    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }


    public void setRotation(float degree) {
        sprite.setRotation(degree);
    }

    public void setRotation(int rotation) {
        sprite.setRotation(rotation);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        sprite.setPosition(x,y);
    }
    public void setColor(Color color){
        sprite.setColor(color.r ,color.g ,color.b ,255);
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }

    public boolean isCollide(DrawableSprite object) {
        return  x < object.x + object.getWidth()  && x + getWidth() > object.x &&
                y < object.y + object.getHeight() && y + getHeight() > object.y;
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Item{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
