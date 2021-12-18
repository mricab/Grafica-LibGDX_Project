package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class SnakeGame extends ApplicationAdapter
{
	private Texture bucketImage;
	private Rectangle bucket;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		// Bucket
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
	}

	@Override
	public void render () {
		// Screen clearing
		ScreenUtils.clear(0, 0, 0.2f, 1); // rgba (Darkblue)

		// Keyboard input
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.y -= 200 * Gdx.graphics.getDeltaTime();

		// Viewport constrains
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;
		if(bucket.y < 0) bucket.y = 0;
		if(bucket.y > 480 - 64) bucket.y = 480 - 64;

		//Redering
		camera.update();
		batch.setProjectionMatrix(camera.combined); //tells the SpriteBatch to use the coordinate system specified by the camera.
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		bucketImage.dispose();
		batch.dispose();
	}
}
