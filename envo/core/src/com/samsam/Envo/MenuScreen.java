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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NghiaTrinh on 8/31/2015.
 */

public class MenuScreen implements Screen,InputProcessor{
    final EnvoGame game;
    OrthographicCamera camera;

    boolean ishide,isdownloading,isStartdownload;
    int xDown,yDown,xUp,yUp,downloadPercent;
    InputStream input = null;
    OutputStream output = null;
    HttpURLConnection connection = null;

    Texture pageImg,pageActiveImg;
    List<CategoryInfo> categories = new ArrayList<CategoryInfo>();
    private static final int pageCount = 3;
    int pageActive=0;


    public MenuScreen(EnvoGame game1,int pageNumber) {
        this.game = game1;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        game.font.setColor(255, 0, 0, 1);
        ishide=false;
        isdownloading=false;
        isStartdownload=false;
        downloadPercent=0;
        pageActive=pageNumber;
        String loadedCat = Helpers.getLoadedCategory().trim();
        String[] loadedCategories = loadedCat.split(";");

        pageImg=new Texture("page.png");
        pageActiveImg=new Texture("activepage.png");
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
                if (index>game.categoryNames.length-1) break;
                Rectangle rect = new Rectangle(px*(j+1)+j*w,v-py*(i+1)-(i+1)*h,w,h);
                boolean isloaded = Helpers.containString(loadedCategories, String.valueOf(index));
                categories.add(new CategoryInfo(game.categoryNames[index],new Texture("category/"+(isloaded?"":"download/")+(index+1)+".jpg"),rect,String.valueOf(index),isloaded,game.downloadUrls[index]));
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
        if (isdownloading) renderDownloading();

        game.batch.end();

        if (ishide) this.dispose();
    }
    private void renderDownloading()
    {
        float loadingY=camera.viewportHeight/2-game.loadingbg.getHeight()/2;
        game.batch.draw(game.loadingbg, 0, loadingY);
        GlyphLayout layout = new GlyphLayout(game.font,"Downloading...");
        game.font.draw(game.batch,layout,camera.viewportWidth/2-layout.width/2,loadingY+80);
        GlyphLayout layout1 = new GlyphLayout(game.font,String.valueOf(downloadPercent)+"%");
        game.font.draw(game.batch, layout1, camera.viewportWidth / 2 - layout1.width / 2, loadingY + 50);
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
        closeConnectionDownload();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        ishide=true;
        Gdx.input.setInputProcessor(null);
        closeConnectionDownload();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        closeConnectionDownload();
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
        if (difX>50&&difX>difY)
        {
            if (pageActive>0) {
                this.game.setScreen(new MenuScreen(game, pageActive - 1));
            }
        }
        //swipe left
        if (difX<-50&&difX<difY)
        {
            if (pageActive<2) {
                this.game.setScreen(new MenuScreen(game, pageActive + 1));
            }
        }
        if (difX<51&&difX>-51) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            for (CategoryInfo cat : categories) {
                if (Helpers.isTouchedInRect(cat.rectangle, v.x, v.y)) {
                    if (cat.isLoaded) game.setScreen(new GameScreen(game,"1",0));
                    else
                    {
                        isdownloading=true;
                        Gdx.input.setInputProcessor(null);
                        downloadFile(cat.getDownloadUrl());
                    }
                }
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

    private boolean downloadFile(String sUrl)
    {
        try {
            URL url = new URL(sUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }

            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream("/sdcard/file_name.extension");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    downloadPercent= (int) (total * 100 / fileLength);
                output.write(data, 0, count);
            }
        } catch (Exception e) {
           return false;
        } finally {
            closeConnectionDownload();
        }
        return  true;
    }

    private void closeConnectionDownload()
    {
        try {
            if (output != null)
                output.close();
            if (input != null)
                input.close();
        } catch (IOException ignored) {
        }

        if (connection != null)
            connection.disconnect();
    }
}
