package com.samsam.pic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PicGame extends Game {
	SpriteBatch batch;
	Texture pic1;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		pic1 = new Texture("1.jpg");
		setScreen(new GameScreen1(this));
	}

	@Override
	public void render () {super.render();}

	@Override
	public void dispose()
	{
		pic1.dispose();
	}
}
