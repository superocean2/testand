package com.samsam.Envo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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

public class MenuScreen implements Screen,InputProcessor{
    final EnvoGame game;
    OrthographicCamera camera;

    boolean ishide;
    int xDown,yDown,xUp,yUp;

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
        int py=80;
        int w=200;
        int h=150;
        float v=camera.viewportHeight;
        for (int i=0;i<3;i++)
        {
            for (int j=0;j<2;j++)
            {
                int index = (j+2*i)+pageNumber*6;
                if (index>categoryNames.length-1) break;
                Rectangle rect = new Rectangle(px*(j+1)+j*w,v-py*(i+1)-(i+1)*h,w,h);
                categories.add(new CategoryInfo(categoryNames[index],new Texture("category/"+(index+1)+".jpg"),rect));
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
            GlyphLayout layout = new GlyphLayout(game.font,cat.getName());
            float fontX;
            if (cat.rectangle.x>220)
            {
                fontX = (cat.rectangle.x + (cat.texture.getWidth())*2 +20)/2-layout.width/2;
            }
            else
            {
                fontX = (cat.rectangle.x + cat.texture.getWidth() +20)/2-layout.width/2;
            }

            float fontY = cat.rectangle.y - layout.height+15;
            game.font.draw(game.batch,layout,fontX,fontY);
        }
        for (int i=0;i<pageCount;i++)
        {
            float x=0;
            float y=15;
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
        Gdx.input.setInputProcessor(this);
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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        xDown=screenX;
        yDown=screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int difX = screenX-xDown;
        int difY= screenY-yDown;
        //swipe right
        if (difX>30&&difX>difY)
        {
            if (pageActive>0) {
                this.game.setScreen(new MenuScreen(game, pageActive - 1));
            }
        }
        //swipe left
        if (difX<-30&&difX<difY)
        {
            if (pageActive<2) {
                this.game.setScreen(new MenuScreen(game, pageActive + 1));
            }
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
