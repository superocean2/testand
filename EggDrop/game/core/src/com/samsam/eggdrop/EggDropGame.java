package com.samsam.eggdrop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class EggDropGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	OrthographicCamera camera;
	Texture cartImage;
	Texture eggImage;
	Texture eggDrop;
	Rectangle cartRec;
	Array<Rectangle> eggs;
	float lastDropTime;
	GameState gameState;
	Texture readyImage;
	Texture gameOverImage;
	BitmapFont font;
	int score;
	int life;
	float baseline;
	Music music;
	Sound hitsound;
	Sound gameoverSound;
	Sound lifeRainSound;
	int level;

	@Override
	public void create() {
		batch= new SpriteBatch();
		background = new Texture("background.png");
		cartImage = new Texture("cart.png");
		readyImage=new Texture("ready.png");
		gameOverImage=new Texture("gameover.png");
		music=Gdx.audio.newMusic(Gdx.files.internal("sound/rain.mp3"));
		hitsound= Gdx.audio.newSound(Gdx.files.internal("sound/hit.mp3"));
		gameoverSound= Gdx.audio.newSound(Gdx.files.internal("sound/gameover.mp3"));
		lifeRainSound= Gdx.audio.newSound(Gdx.files.internal("sound/explode.wav"));
		cartRec =new Rectangle();
		cartRec.width=85;
		cartRec.height=41;
		eggImage = new Texture("egg.png");
		eggDrop = new Texture("egg_drop.png");
		camera=new OrthographicCamera();
		camera.setToOrtho(false,800,480);
		eggs =new Array<Rectangle>();
		initRainDrop();
		gameState=GameState.Start;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/new.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 40;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();

		font.setColor(1, 0, 0, 1);
		baseline=480-30;
		music.setLooping(true);
		music.setVolume((float) 0.5);
		music.play();
		resetGame();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateWorld();
		drawworld();
	}

	private  void updateWorld()
	{
		if (Gdx.input.justTouched()) {
			if (gameState == GameState.Start) {
				gameState = GameState.Running;
			}
			if (gameState == GameState.GameOver) {
				resetGame();
			}
		}
		if (Gdx.input.isTouched())
		{
				if (gameState == GameState.Running) {
					Vector3 touchPosition = new Vector3();
					touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					camera.unproject(touchPosition);
					cartRec.x = touchPosition.x - 85 / 2;
					if (cartRec.x > 800 - 85) {
						cartRec.x = 800 - 85;
					}
					if (cartRec.x < 0) cartRec.x = 0;
				}
		}

		if (gameState==GameState.Running) {
			level=(int)Math.ceil((double)score/10)+1;
			if (score>level*10) level++;
			if (TimeUtils.nanoTime() - lastDropTime > 1000000000*1.2) {
				initRainDrop();
			}
		}

	}
	private  void drawworld()
	{
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(cartImage, cartRec.x, cartRec.y);
		if (gameState==GameState.Start) {
			batch.draw(readyImage, 800 / 2 - readyImage.getWidth() / 2, 480 / 2 - readyImage.getHeight() / 2);
		}
		if (gameState==GameState.GameOver) {
			batch.draw(gameOverImage, 800 / 2 - gameOverImage.getWidth() / 2, 480 / 2 - gameOverImage.getHeight() / 2);
		}
		font.draw(batch,"Life: ",20,baseline);
		font.draw(batch,String.valueOf(life),150,baseline);
		font.draw(batch,"Score: ",800-320,baseline);
		font.draw(batch,String.valueOf(score),800-150,baseline);
		batch.end();


		if (gameState==GameState.Running) {
			Iterator<Rectangle> iter = eggs.iterator();

			while (iter.hasNext()) {
				Rectangle rect = iter.next();
				rect.y -= 100*level*Gdx.graphics.getDeltaTime();
				batch.begin();
				batch.draw(eggImage, rect.x, rect.y);
				if (rect.overlaps(cartRec)) {
					hitsound.play();
					score++;
					iter.remove();
				}
				batch.end();
				if (rect.y < 42)
				{
					iter.remove();
					lifeRainSound.play();
					life--;
					if (life==0)
					{
						gameoverSound.play();
						gameState=GameState.GameOver;
					}
				}

			}
		}
	}
	private  void resetGame()
	{
		life=3;
		score=0;
		level=1;
		gameState=GameState.Start;
		cartRec.x=800/2- cartImage.getWidth()/2;
		cartRec.y=10;
	}
	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		background.dispose();
		batch.dispose();
		cartImage.dispose();
		gameOverImage.dispose();
		readyImage.dispose();
		music.dispose();
		hitsound.dispose();
		gameoverSound.dispose();
		lifeRainSound.dispose();
	}

	private void initRainDrop()
	{
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 30);
		raindrop.y=480;
		raindrop.width=30;
		raindrop.height=42;
		eggs.add(raindrop);
		lastDropTime=TimeUtils.nanoTime();
	}
	enum GameState {
		Start, Running, GameOver
	}
}
