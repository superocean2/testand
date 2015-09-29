package com.samsam.Envo;

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

    final EnvoGame game;
    String category;
    int screenId;
    OrthographicCamera camera;
    boolean ishide;
    Texture picture;
    Rectangle rectLeft;
    Rectangle rectRight;
    String[] names = {"Pic1","Pic2","Pic3"};

    public GameScreen(EnvoGame game, String category, int screenId) {
        this.game = game;
        this.category = category;
        this.screenId = screenId;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        game.font.setColor(255, 255, 255, 1);
        ishide=false;

        picture = new Texture(Gdx.files.local("pictures/"+category+"/"+screenId+".jpg"));
        rectLeft = new Rectangle(10,6,game.left.getWidth(),game.left.getHeight());
        rectRight = new Rectangle(camera.viewportWidth-10-game.right.getWidth(),6,game.right.getWidth(),game.right.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(picture, 15, 125);
        game.batch.draw(game.bottombg, 0, 0);
        game.batch.draw(game.left, rectLeft.x, rectLeft.y);
        game.batch.draw(game.right, rectRight.x, rectRight.y);
        GlyphLayout layout = new GlyphLayout(game.font,names[screenId]);
        game.font.draw(game.batch,layout,camera.viewportWidth/2-layout.width/2,game.bottombg.getHeight()/2+layout.height/2);
        game.batch.end();
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
