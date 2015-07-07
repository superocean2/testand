package samsam.colortest.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ColorTest extends Game {
	SpriteBatch batch;
	BitmapFont font;
	BitmapFont fontScore;
	Texture background;
	Texture button;
	@Override
	public void create () {
		batch = new SpriteBatch();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/droid-i.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 20;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
		font.setColor(1, 0, 0, 1);

		FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("font/classic.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter1.size = 30;
		fontScore = generator1.generateFont(parameter1); // font size 12 pixels
		fontScore.setColor(255,255,255,1);
		generator1.dispose();

		background = new Texture("bg_colortest.png");
		button = new Texture("button.png");
		setScreen(new StartScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		fontScore.dispose();
		background.dispose();
		button.dispose();
	}
}
