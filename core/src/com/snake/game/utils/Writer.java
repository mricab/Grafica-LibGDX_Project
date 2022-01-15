package com.snake.game.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snake.game.handlers.AssetController;
import com.snake.game.primitives.Position;

public class Writer {

    static private BitmapFont Font;

    public Writer(Color color) {
        Font = AssetController.instance().get(AssetController.PIXEL_FONT);
        Font.setColor(color);
    }

    static public void Color(Color color) {
        Font.setColor(color);
    }

    static public void Draw(SpriteBatch batch, String msg, Position pos) {
        Font.draw(batch, msg, pos.X, pos.Y);
    }

}
