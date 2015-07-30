package com.samsam.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TetrisGame extends Game {
	SpriteBatch batch;
	Texture background;
	Texture block;
	Texture left;
	Texture right;
	Texture down;
	Texture rotate;
	Texture loudSpeaker;
	Texture muteSpeaker;
	Texture pause;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("tetris.png");
		block = new Texture("block.png");
		left = new Texture("left.png");
		right = new Texture("right.png");
		down = new Texture("down.png");
		rotate = new Texture("rotate.png");
		loudSpeaker = new Texture("lound.png");
		muteSpeaker = new Texture("mute.png");
		pause = new Texture("pause.png");
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
		block.dispose();
		left.dispose();
		right.dispose();

	}
}
