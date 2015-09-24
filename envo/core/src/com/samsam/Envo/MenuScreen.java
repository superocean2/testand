package com.samsam.Envo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NghiaTrinh on 8/31/2015.
 */

public class MenuScreen implements Screen{
    final EnvoGame game;
    OrthographicCamera camera;

    boolean ishide;

    Texture page,pageActive;
    List<CategoryInfo> categories = new ArrayList<CategoryInfo>();
    String[] categoryNames = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
    public MenuScreen(EnvoGame game,int pageNumber) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        game.font.setColor(255, 0, 0, 1);
        ishide=false;

        page=new Texture("page.png");
        pageActive=new Texture("activepage.png");
        int page=1+pageNumber*6;
        for (int i=0;i<6;i++)
        {
            Rectangle rect = new Rectangle(20+(i==0?i*200:i*200+20),20+(i==0?i*150:i*150+20),200,150);
            categories.add(new CategoryInfo(categoryNames[page+i],new Texture("category/"+page+i+".jpg"),rect));
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        for (CategoryInfo cat:categories)
        {
            game.batch.draw(cat.texture,cat.rectangle.x,cat.rectangle.y);
        }
        game.batch.end();

        if (Gdx.input.justTouched())
        {
            Vector3 v= new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);

        }

        if (ishide) this.dispose();
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

    }
}
