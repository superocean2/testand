package samsam.nghia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by NghiaTrinh on 6/19/2015.
 */
public class GameScreen implements Screen {

    final  RainDrop game;

    SpriteBatch batch;
    Texture background;
    OrthographicCamera camera;
    Texture bucketImage;
    Texture rainDropImage;
    Rectangle bucketRec;
    Array<Rectangle> rainDrops;
    float lastDropTime;

    public GameScreen(RainDrop game) {
        this.game = game;

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
        rainDrops=new Array<Rectangle>();
        initRainDrop();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(bucketImage,bucketRec.x,bucketRec.y);
        batch.end();
        if (Gdx.input.isTouched())
        {
            Vector3 touchPosition = new Vector3();
            touchPosition.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPosition);
            bucketRec.x=touchPosition.x-64/2;
            if (bucketRec.x>800-64)
            {
                bucketRec.x=800-64;
            }
            if (bucketRec.x<0) bucketRec.x=0;
        }

        if (TimeUtils.nanoTime()-lastDropTime>1000000000*1.20)
        {
            initRainDrop();
        }
        Iterator<Rectangle> iter = rainDrops.iterator();

        while (iter.hasNext())
        {
            Rectangle rect = iter.next();
            rect.y-=4;
            batch.begin();
            batch.draw(rainDropImage,rect.x,rect.y);
            if (rect.overlaps(bucketRec))
            {
                iter.remove();
            }
            batch.end();
            if (rect.y<-64) iter.remove();

        }
    }

    @Override
    public void show() {

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
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        bucketImage.dispose();
        batch.dispose();
    }
    private void initRainDrop()
    {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y=480;
        raindrop.width=64;
        raindrop.height=64;
        rainDrops.add(raindrop);
        lastDropTime= TimeUtils.nanoTime();
    }
}
