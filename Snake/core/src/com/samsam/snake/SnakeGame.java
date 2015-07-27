package com.samsam.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;

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
	Texture newgame;
	Texture gameover;
	Texture highscore;
	Rectangle worldScreen;
	BitmapFont font;
	Sound click;
	Sound eat;
	Sound hit;
	Sound extraFoodSound;

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
		newgame = new Texture("newgame.png");
		gameover = new Texture("gameover.png");
		highscore=new Texture("highscore.png");
		worldScreen = new Rectangle(45,250,330,390);
		click = Gdx.audio.newSound(Gdx.files.internal("sound/click.wav"));
		eat = Gdx.audio.newSound(Gdx.files.internal("sound/eat.wav"));
		hit = Gdx.audio.newSound(Gdx.files.internal("sound/hit.wav"));
		extraFoodSound = Gdx.audio.newSound(Gdx.files.internal("sound/extrafood.wav"));

		FreeTypeFontGenerator generator6 = new FreeTypeFontGenerator(Gdx.files.internal("font/chuviet1.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter6 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter6.size = 40;
		//parameter6.characters="123456789";
		font = generator6.generateFont(parameter6);
		font.setColor(255, 255, 255, 1);
		generator6.dispose();

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
		font.dispose();
		click.dispose();
		hit.dispose();
		eat.dispose();
		extraFoodSound.dispose();
	}
}
