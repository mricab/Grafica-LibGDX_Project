package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snake.game.game.Input.InputController;
import com.snake.game.handlers.AssetController;
import com.snake.game.game.GameController;

public class SnakeGame extends ApplicationAdapter
{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private GameController gameController;
	private InputController inputController;

	@Override
	public void create() {
		AssetController.instance().loadAssets();
		inputController = new InputController();
		inputController.start();
		gameController = new GameController(inputController);
		gameController.start();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render() {

		// Screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		// Render
		batch.begin();
			gameController.render(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		AssetController.instance().dispose();
		inputController.cancel();
	}


}
