package samsam.colortest.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by NghiaTrinh on 7/6/2015.
 */
public class StartScreen implements Screen {
    final ColorTest game;
    OrthographicCamera camera;
    Rectangle rectStart;
    LanguagesManager languagesManager;
    public StartScreen(final ColorTest test) {
        game=test;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);
        rectStart = new Rectangle(camera.viewportWidth/2- game.button.getWidth()/2,250,game.button.getWidth(),game.button.getHeight());
        languagesManager=LanguagesManager.getInstance();
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

        game.fontResultTitle.setColor(1,0,0,1);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(game.fontResultTitle, languagesManager.getString("gameDescription"));
        game.fontResultTitle.draw(game.batch, layout, camera.viewportWidth / 2 - layout.width / 2, 700);

        GlyphLayout layout1 = new GlyphLayout();
        layout1.setText(game.fontResultTitle, languagesManager.getString("gameDescription1"));
        game.fontResultTitle.draw(game.batch, layout1, camera.viewportWidth / 2 - layout1.width / 2, 650);

        game.fontResultTitle.setColor(1,1,1,1);
        GlyphLayout layout2 = new GlyphLayout();
        layout2.setText(game.fontResultTitle, languagesManager.getString("start"));
        game.fontResultTitle.draw(game.batch,layout2,camera.viewportWidth/2-layout2.width/2,292);
        game.batch.end();

        if (Gdx.input.justTouched())
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
