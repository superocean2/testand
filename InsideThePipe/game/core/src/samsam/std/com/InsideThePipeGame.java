package samsam.std.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InsideThePipeGame extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	Texture pipe_begin_1;
	Texture pipe_begin_2;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera=new OrthographicCamera();
		camera.setToOrtho(false,800,480);
		pipe_begin_1=new Texture("pipe_begin_1.png");
		pipe_begin_2=new Texture("pipe_begin_2.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		batch.end();
	}
}
