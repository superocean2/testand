package com.samsam.happybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class HappyBird extends Game {
	SpriteBatch batch;
	ActionResolver actionResolver;
	Texture background;
	Texture bird;
	Texture birdUp;
	Texture birdDown;
	Texture birdDie;
	Texture gameover;
	Texture ready;
	TextureRegion floor;
	TextureRegion ceiling;
	TextureRegion rockTop;
	TextureRegion rockBottom;
	BitmapFont font;

	public HappyBird(ActionResolver actionResolver){this.actionResolver=actionResolver;}

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		bird = new Texture("bird.png");
		birdUp = new Texture("bird-up.png");
		birdDown = new Texture("bird-down.png");
		birdDie = new Texture("bird-die.png");
		gameover = new Texture("gameover.png");
		ready = new Texture("ready.png");
		floor = new TextureRegion(new Texture("ground.png"));
		ceiling = new TextureRegion(floor);
		ceiling.flip(true,true);
		rockBottom = new TextureRegion(new Texture("rock.png"));
		rockTop = new TextureRegion(rockBottom);
		rockTop.flip(false,true);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 40;
		parameter.characters="0123456789";
		font = generator.generateFont(parameter);
		font.setColor(0, 0, 0, 1);
		generator.dispose();
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
		bird.dispose();
		birdUp.dispose();
		birdDown.dispose();
		birdDie.dispose();
		gameover.dispose();
		ready.dispose();
	}
}
