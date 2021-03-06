package com.samsam.xmasblock;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;

public class XmasBlockGame extends Game {
	SpriteBatch batch;
	Texture background;
	Texture block;
	Texture blockmini;
	Texture left;
	Texture right;
	//Texture down;
	Texture rotate;
	Texture loudSpeaker;
	Texture muteSpeaker;
	Texture pause;
	Texture ready;
	Texture gameOver;
	Texture highScore;
	Texture youhavenewhighscore;
	Texture newGame;
	Texture quit;
	Texture resume;
	Texture overlay;
	Texture worldScoreBg;
	Texture shareFb;
	Texture cancel;
	Texture hissBrick;
	Texture snow;
	Rectangle rectScreen;
	int mainBlockWidth;
	int mainBlockHeight;
	BitmapFont font;
	Music bacgroundMusic;
	Sound eat;
	Sound hit;
	ActionResolver actionResolver;


	public XmasBlockGame(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("tetrisxmas.png");
		block = new Texture("block2.png");
		blockmini = new Texture("block2mini.png");
		left = new Texture("left.png");
		right = new Texture("right.png");
		//down = new Texture("down.png");
		rotate = new Texture("rotate.png");
		loudSpeaker = new Texture("lound.png");
		muteSpeaker = new Texture("mute.png");
		pause = new Texture("pause.png");
		gameOver=new Texture("gameover.png");
		youhavenewhighscore = new Texture("youhavenewhighscore.png");
		highScore = new Texture("yourscore-highscore.png");
		newGame = new Texture("newgame.png");
		quit = new Texture("quit.png");
		ready = new Texture("ready.png");
		resume = new Texture("resume.png");
		overlay = new Texture("gameoverlayxmas.png");
		shareFb = new Texture("share.png");
		cancel = new Texture("cancelxmas.png");
		hissBrick = new Texture("merryxmas.png");
		snow = new Texture("iceflower.png");
		bacgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/bg.ogg"));
		eat = Gdx.audio.newSound(Gdx.files.internal("sound/eat.mp3"));
		hit = Gdx.audio.newSound(Gdx.files.internal("sound/hit.mp3"));


		rectScreen = new Rectangle(7.5f,153.5f,300,570);
		mainBlockWidth=30;
		mainBlockHeight=30;

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
		block.dispose();
		left.dispose();
		right.dispose();
		//down.dispose();
		rotate.dispose();
		muteSpeaker.dispose();
		loudSpeaker.dispose();
		pause.dispose();
		gameOver.dispose();
		highScore.dispose();
		newGame.dispose();
		quit.dispose();
		ready.dispose();
		resume.dispose();
		overlay.dispose();
		blockmini.dispose();
		bacgroundMusic.dispose();
		eat.dispose();
		hit.dispose();
		worldScoreBg.dispose();
		shareFb.dispose();
		cancel.dispose();
		hissBrick.dispose();
	}
}
