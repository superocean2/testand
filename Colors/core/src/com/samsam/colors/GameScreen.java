package com.samsam.colors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by NghiaTrinh on 8/31/2015.
 */
public class GameScreen implements Screen {

    final ColorGame game;
    String color;
    int screenId;
    OrthographicCamera camera;
    boolean ishide;
    Texture picture;
    Rectangle rectLeft;
    Rectangle rectRight;


    public GameScreen(ColorGame game, String color, int screenId) {
        this.game = game;
        this.color = color;
        this.screenId = screenId;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 1280);
        game.font.setColor(255, 255, 255, 1);
        ishide=false;

        picture = new Texture(color.toLowerCase()+"/"+screenId+".png");
        rectLeft = new Rectangle(10,6,game.left.getWidth(),game.left.getHeight());
        rectRight = new Rectangle(camera.viewportWidth-10-game.right.getWidth(),6,game.right.getWidth(),game.right.getHeight());
    }

    @Override
    public void render(float delta) {
        if (color.toLowerCase().equals("white"))
        {
            Gdx.gl.glClearColor(0, 0, 0, 1);
        }
        else {
            Gdx.gl.glClearColor(255, 255, 255, 1);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(picture, 0, 0);
        game.batch.draw(game.bottombg, -1, 0);
        game.batch.draw(game.left, rectLeft.x, rectLeft.y);
        game.batch.draw(game.right, rectRight.x, rectRight.y);
        GlyphLayout layout = new GlyphLayout(game.font,color);
        game.font.draw(game.batch,layout,camera.viewportWidth/2-layout.width/2,game.bottombg.getHeight()/2+layout.height/2);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectLeft, v.x, v.y)) {
                if (screenId>1)
                game.setScreen(new GameScreen(game, color, screenId-1));
                else game.setScreen(new MenuScreen(game));
            }
            if (Helpers.isTouchedInRect(rectRight,v.x,v.y))
            {
                if (screenId<10)
                {
                    game.setScreen(new GameScreen(game,color,screenId+1));
                }
                else
                {
                    game.setScreen(new MenuScreen(game));
                }
            }
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
        ishide=true;
    }

    @Override
    public void dispose() {
        picture.dispose();
    }
}
