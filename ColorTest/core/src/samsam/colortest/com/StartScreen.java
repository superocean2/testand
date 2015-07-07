package samsam.colortest.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by NghiaTrinh on 7/6/2015.
 */
public class StartScreen implements Screen {
    final ColorTest game;
    OrthographicCamera camera;
    Rectangle rectStart;
    public StartScreen(final ColorTest test) {
        game=test;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);
        rectStart = new Rectangle(120,250,game.button.getWidth(),game.button.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        game.batch.draw(game.button,rectStart.getX(),rectStart.getY());
        game.font.draw(game.batch, "See different", 50, 600);

        game.fontScore.draw(game.batch,"Start",200,280);
        game.batch.end();

        if (Gdx.input.isTouched())
        {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectStart,v.x,v.y)) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
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

    }
}
