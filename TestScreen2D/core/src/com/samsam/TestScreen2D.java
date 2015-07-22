package com.samsam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TestScreen2D extends ApplicationAdapter {

	Stage stage;

	public class Plane extends Actor
	{
		Texture plane = new Texture("plane1.png");
		float planeX=0;
		float planeY=0;
		boolean started=false;

		public Plane() {
			setBounds(planeX,planeY,plane.getWidth(),plane.getHeight());
			addListener(new InputListener() {
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					((Plane)event.getTarget()).started = true;
					return true;
				}
			});
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(plane,planeX,planeY);
		}

		@Override
		public void act(float delta) {
			if (started)
			{
				planeX+=15;
			}
		}
	}
	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		Plane plane = new Plane();
		plane.setTouchable(Touchable.enabled);
		stage.addActor(plane);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0.2f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
