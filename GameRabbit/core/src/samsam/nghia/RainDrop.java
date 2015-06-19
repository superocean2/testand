package samsam.nghia;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import sun.font.TrueTypeFont;

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
    Array<Rectangle> rainDrops;
    float lastDropTime;
    GameState gameState;
    Texture readyImage;
    Texture gameOverImage;
    BitmapFont font;
    int score;
    int life;
    float baseline;

    @Override
    public void create() {
        batch= new SpriteBatch();
        background = new Texture("background.png");
        bucketImage = new Texture("bucket.png");
        readyImage=new Texture("ready.png");
        gameOverImage=new Texture("gameover.png");
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
        gameState=GameState.Start;
        font =  new BitmapFont(Gdx.files.internal("arial.fnt"));
        font.setColor(0,0,1,1);
        baseline=480-30;
        resetGame();
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
        if (gameState==GameState.Start) {
            batch.draw(readyImage, 800 / 2 - readyImage.getWidth() / 2, 480 / 2 - readyImage.getHeight() / 2);
        }
        if (gameState==GameState.GameOver) {
            batch.draw(gameOverImage, 800 / 2 - gameOverImage.getWidth() / 2, 480 / 2 - gameOverImage.getHeight() / 2);
        }
        font.draw(batch,"Life: ",20,baseline);
        font.draw(batch,String.valueOf(life),120,baseline);
        font.draw(batch,"Score: ",800-350,baseline);
        font.draw(batch,String.valueOf(score),800-200,baseline);
        batch.end();
        if (Gdx.input.isTouched())
        {
            if (gameState==GameState.Start) {
                gameState = GameState.Running;
            }
            if (gameState==GameState.GameOver)
            {
                gameState=GameState.Start;
                resetGame();
            }
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

        if (gameState==GameState.Running) {
            if (TimeUtils.nanoTime() - lastDropTime > 1000000000 * 1.20) {
                initRainDrop();
            }
            Iterator<Rectangle> iter = rainDrops.iterator();

            while (iter.hasNext()) {
                Rectangle rect = iter.next();
                rect.y -= 4;
                batch.begin();
                batch.draw(rainDropImage, rect.x, rect.y);
                if (rect.overlaps(bucketRec)) {
                    score++;
                    iter.remove();
                }
                batch.end();
                if (rect.y < -64)
                {
                    life--;
                    if (life==0)
                    {
                        gameState=GameState.GameOver;
                    }
                    iter.remove();
                }

            }
        }
    }

    private  void resetGame()
    {
        life=3;
        score=0;
        gameState=GameState.Start;
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
        gameOverImage.dispose();
        readyImage.dispose();
    }

    private void initRainDrop()
    {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0,800-64);
        raindrop.y=480;
        raindrop.width=64;
        raindrop.height=64;
        rainDrops.add(raindrop);
        lastDropTime=TimeUtils.nanoTime();
    }
    enum GameState {
        Start, Running, GameOver
    }
}
