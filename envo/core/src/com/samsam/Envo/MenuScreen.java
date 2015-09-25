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

    Texture pageImg,pageActiveImg;
    List<CategoryInfo> categories = new ArrayList<CategoryInfo>();
    String[] categoryNames = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
    private static final int pageCount = 3;
    int pageActive=0;

    public MenuScreen(EnvoGame game1,int pageNumber) {
        this.game = game1;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        game.font.setColor(255, 0, 0, 1);
        ishide=false;
        pageActive=pageNumber;

        pageImg=new Texture("page.png");
        pageActiveImg=new Texture("activepage.png");
        int page=1+pageNumber*6;
        int px=20;
        int py=85;
        int w=200;
        int h=150;
        for (int i=0;i<2;i++)
        {
            for (int j=0;j<3;j++)
            {
                Rectangle rect = new Rectangle(px*(i+1)+i*w,py*(j+1)+j*h,w,h);
                categories.add(new CategoryInfo(categoryNames[page+i],new Texture("category/"+(page+i)+".jpg"),rect));
            }

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
        for (int i=0;i<pageCount;i++)
        {
            float x=0;
            float y=35;
            int wp=15;
            int sp=20;
            int wrect =(wp+sp)*pageCount;
            Rectangle rectPage = new Rectangle(camera.viewportWidth/2-wrect/2,y,wrect,wp);
            x=rectPage.x+i*wp +i*sp;
            game.batch.draw(i==pageActive?pageActiveImg:pageImg,x,y);
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
