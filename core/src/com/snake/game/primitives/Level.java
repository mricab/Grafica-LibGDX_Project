package com.snake.game.primitives;

public enum Level {
    lvl0(300),
    lvl1(250),
    lvl2(200),
    lvl3(150),
    lvl4(100);

    public long speed;

    Level(long speed) {
        this.speed = speed;
    }

    public Level next() {
        int i = this.ordinal();
        return Level.values()[(i + 1) % Level.values().length];
    }
}
