package samsam.nghia;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by NghiaTrinh on 6/17/2015.
 */
public class RainDrop extends ApplicationAdapter {

    SpriteBatch batch;
    Texture background;
    OrthographicCamera camera;
    Texture bucket;
    float bucketPosition_X;
    float bucketPosition_Y=10;
    Texture rainDrop;

    @Override
    public void create() {
        batch= new SpriteBatch();
        background = new Texture("background.png");
        bucket = new Texture("bucket.png");
        bucketPosition_X=800/2-bucket.getWidth()/2;
        rainDrop = new Texture("droplet.png");
        camera=new OrthographicCamera();
        camera.setToOrtho(false,800,480);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(bucket,bucketPosition_X,bucketPosition_Y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        background.dispose();
        batch.dispose();
        bucket.dispose();
    }
}
