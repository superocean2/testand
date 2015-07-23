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
	Texture resume;
	Texture quit;
	Texture getReady;
	Texture loudSpeaker;
	Texture muteSpeaker;

	int GAME_SCREEN_PADDING_BOTTOM=250;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("snake-bg.png");
		snakeHead = new Texture("snake-head.png");
		snakeTail = new Texture("snake-tail.png");
		food = new Texture("food.png");
		exTraFood = new Texture("extra-food.png");
		turnLeft = new Texture("left.png");
		turnRight = new Texture("right.png");
		turnUp = new Texture("up.png");
		turnDown = new Texture("down.png");
		pause = new Texture("pause.png");
		resume = new Texture("resume.png");
		quit = new Texture("quit.png");
		getReady = new Texture("ready.png");
		loudSpeaker = new Texture("loud-speaker.png");
		muteSpeaker = new Texture("mute-speaker.png");

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
		food.dispose();
		exTraFood.dispose();
		turnLeft.dispose();
		turnRight.dispose();
		turnUp.dispose();
		turnDown.dispose();
		pause.dispose();
		resume.dispose();
		quit.dispose();
		getReady.dispose();
		loudSpeaker.dispose();
		muteSpeaker.dispose();
	}
}
