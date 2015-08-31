package com.samsam.colors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ColorGame extends Game {
	SpriteBatch batch;
	Texture bottombg;
	Texture left;
	Texture right;
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		bottombg=new Texture("bottom_bg.png");
		left = new Texture("left.png");
		right=new Texture("right.png");
		FreeTypeFontGenerator generator6 = new FreeTypeFontGenerator(Gdx.files.internal("font/chuviet1.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter6 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter6.size = 60;
		//parameter6.characters="123456789";
		font = generator6.generateFont(parameter6);
		font.setColor(255, 255, 255, 1);
		generator6.dispose();

		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose()
	{
		bottombg.dispose();
		left.dispose();
		right.dispose();
		font.dispose();
	}
}
