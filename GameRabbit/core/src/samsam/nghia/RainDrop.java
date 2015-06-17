package samsam.nghia;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by NghiaTrinh on 6/17/2015.
 */
public class RainDrop extends ApplicationAdapter {

    SpriteBatch batch;
    Texture background;
    OrthographicCamera camera;
    Texture bucketImage;
    Texture rainDropImage;
    Rectangle bucketRec;
    ArrayList<Rectangle> rainDrops;

    @Override
    public void create() {
        batch= new SpriteBatch();
        background = new Texture("background.png");
        bucketImage = new Texture("bucket.png");
        bucketRec=new Rectangle();
        bucketRec.x=800/2-bucketImage.getWidth()/2;
        bucketRec.y=10;
        bucketRec.width=64;
        bucketRec.height=64;
        rainDropImage = new Texture("droplet.png");
        camera=new OrthographicCamera();
        camera.setToOrtho(false,800,480);
        rainDrops=new ArrayList<Rectangle>();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(bucketImage,bucketRec.x,bucketRec.y);
        batch.end();
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
        bucketImage.dispose();
    }

    private void initRainDrop()
    {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0,800-64);
        raindrop.y=480;
        raindrop.width=64;
        raindrop.height=64;
        rainDrops.add(raindrop);
    }
}
