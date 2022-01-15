package com.snake.game.primitives;

import com.badlogic.gdx.math.MathUtils;

public class Position {
    public int X;
    public int Y;

    public Position(int X, int Y) {
        this.X = X;
        this.Y = Y;
    };

    public Position(Position pos) {
        this.X = pos.X;
        this.Y = pos.Y;
    }

    public void change(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public Position move(Direction dir, Position max) {
        switch (dir) {
            case UP:    Y = dec(Y, max.Y);      break;
            case DOWN:  Y = (Y + 1) % max.Y;    break;
            case LEFT:  X = dec(X, max.X);      break;
            case RIGHT: X = (X + 1) % max.X;    break;
        }
        return this;
    }

    public Position next(Direction dir, Position max) {
        int X2 = X, Y2 = Y;
        switch (dir) {
            case UP:    Y2 = dec(Y, max.Y);      break;
            case DOWN:  Y2 = (Y + 1) % max.Y;    break;
            case LEFT:  X2 = dec(X, max.X);      break;
            case RIGHT: X2 = (X + 1) % max.X;    break;
        }
        return new Position(X2, Y2);
    }

    private int dec(int N, int maxN) {
        if(N - 1 >= 0) return N - 1;
        else return maxN-1;
    }

    static public Position random(Position max) {
        return new Position(
            MathUtils.random(0, max.X-1),
            MathUtils.random(0, max.Y-1)
        );
    }
}
