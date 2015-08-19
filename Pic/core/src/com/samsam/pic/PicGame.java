package com.samsam.pic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PicGame extends Game {
	SpriteBatch batch;
	Texture human;
	Texture muteHuman;
	Texture animal;
	Texture muteAnimal;
	Texture vietnamese;
	Texture english;
	Texture left;
	Texture right;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		human = new Texture("human.png");
		muteHuman = new Texture("mutehuman.png");
		animal = new Texture("animal.png");
		muteAnimal = new Texture("muteanimal.png");
		vietnamese = new Texture("vietnamese.png");
		english = new Texture("english.png");
		left = new Texture("left.png");
		right = new Texture("right.png");
		setScreen(new GameScreen(this,new GameScreenInfo(1,false,false,false)));
	}

	@Override
	public void render () {super.render();}

	@Override
	public void dispose()
	{
		batch.dispose();
		human.dispose();
		muteHuman.dispose();
		animal.dispose();
		muteAnimal.dispose();
		english.dispose();
		vietnamese.dispose();
		left.dispose();
		right.dispose();
	}
}
