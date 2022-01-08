package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snake.game.game.AssetController;
import com.snake.game.game.GameController;

public class SnakeGame extends ApplicationAdapter
{
	private SpriteBatch batch;
	private GameController game;

	@Override
	public void create() {
		AssetController.instance().loadAsset();
		batch = new SpriteBatch();
		game = new GameController();
	}

	@Override
	public void render() {
		game.update(Gdx.graphics.getDeltaTime());
		clearScreen();
		batch.begin();
		game.render(batch);
		batch.end();
	}

	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void dispose() {
		batch.dispose();
		AssetController.instance().dispose();
	}
}
