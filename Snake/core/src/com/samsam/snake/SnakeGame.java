package com.samsam.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;

public class SnakeGame extends Game {
	SpriteBatch batch;
	Texture background;
	Texture snakeHeadUp;
	Texture snakeHeadRight;
	Texture snakeHeadLeft;
	Texture snakeHeadDown;
	Texture snakeTail;
	Texture food;
	Texture exTraFood;
	Texture turnLeft;
	Texture turnRight;
	Texture turnUp;
	Texture turnDown;
	Texture resume;
	Texture quit;
	Texture getReady;
	Texture pause;
	Texture loudSpeaker;
	Texture muteSpeaker;
	Texture newgame;
	Texture gameover;
	Texture highscore;
	Texture overlay;
	Texture worldScoreSubmit;
	Texture cancel;
	Texture sharefb;
	Rectangle worldScreen;
	BitmapFont font;
	Sound eat;
	Sound hit;
	Sound extraFoodSound;
	ActionResolver actionResolver;

	public SnakeGame(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("snake-bg.png");
		snakeHeadUp = new Texture("snake-head-up.png");
		snakeHeadRight = new Texture("snake-head-right.png");
		snakeHeadDown = new Texture("snake-head-down.png");
		snakeHeadLeft = new Texture("snake-head-left.png");
		snakeTail = new Texture("snake-tail.png");
		food = new Texture("food.png");
		exTraFood = new Texture("extra-food.png");
		turnLeft = new Texture("left.png");
		turnRight = new Texture("right.png");
		turnUp = new Texture("up.png");
		turnDown = new Texture("down.png");
		resume = new Texture("resume.png");
		quit = new Texture("quit.png");
		getReady = new Texture("ready.png");
		pause = new Texture("pause1.png");
		loudSpeaker = new Texture("loud-speaker.png");
		muteSpeaker = new Texture("mute-speaker.png");
		newgame = new Texture("newgame.png");
		gameover = new Texture("gameover.png");
		highscore=new Texture("highscore.png");
		overlay = new Texture("overlay.png");
		worldScoreSubmit = new Texture("worldscoresubmit.png");
		cancel = new Texture("cancel.png");
		sharefb=new Texture("sharefb.png");
		worldScreen = new Rectangle(48,258,320,380);
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
		snakeHeadUp.dispose();
		snakeHeadRight.dispose();
		snakeHeadLeft.dispose();
		snakeHeadDown.dispose();
		snakeTail.dispose();
		food.dispose();
		exTraFood.dispose();
		turnLeft.dispose();
		turnRight.dispose();
		turnUp.dispose();
		turnDown.dispose();
		resume.dispose();
		quit.dispose();
		getReady.dispose();
		pause.dispose();
		loudSpeaker.dispose();
		muteSpeaker.dispose();
		overlay.dispose();
		worldScoreSubmit.dispose();
		cancel.dispose();
		sharefb.dispose();
		font.dispose();
		hit.dispose();
		eat.dispose();
		extraFoodSound.dispose();
	}
}
