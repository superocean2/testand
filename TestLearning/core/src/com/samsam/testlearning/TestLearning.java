package com.samsam.testlearning;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

public class TestLearning extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	@Override
	public void create () {
		WarpClient.initialize("ca139cc6ff5191e6300b9c53ae5b035a52797d64b56852aaafb1bba450d0f552","cfd8f357f4bcfb13a764a9bbc3a6986f6dabb7b8571f63d1e11a15b6f82240e7");
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		WarpClient myGame = null;
		try {
			myGame = WarpClient.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		myGame.addConnectionRequestListener(new ConnectionGame());
		try {
			WarpClient.getInstance().connectWithUserName("Jonathan");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
