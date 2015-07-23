package com.samsam.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SnakeGame extends Game {
	SpriteBatch batch;
	Texture background;
	Texture snakeHead;
	Texture snakeTail;
	Texture food;
	Texture exTraFood;
	Texture turnLeft;
	Texture turnRight;
	Texture turnUp;
	Texture turnDown;
	Texture pause;
	Texture resumse;
	Texture loudSpeaker;
	Texture muteSpeaker;
	Texture getReady;
	int GAME_SCREEN_PADDING_BOTTOM=250;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("snake-bg.png");
		snakeHead = new Texture("snake-head.png");
		snakeTail = new Texture("snake-tail.png");
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		background.dispose();
		snakeHead.dispose();
		snakeTail.dispose();
	}
}
