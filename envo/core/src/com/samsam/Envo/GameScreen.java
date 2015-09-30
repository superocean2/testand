package com.samsam.Envo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.io.File;

/**
 * Created by NghiaTrinh on 8/31/2015.
 */
public class GameScreen implements Screen,InputProcessor {

    final EnvoGame game;
    String category;
    int screenId;
    OrthographicCamera camera;
    boolean ishide;
    Texture picture;
    Rectangle rectLeft;
    Rectangle rectRight;
    Rectangle rectBack;
    Rectangle rectTop;
    Rectangle rectSpeaker;
    Rectangle rectPicture;
    String[] names;
    int xDown,yDown,xUp,yUp;
    Music read;

    public GameScreen(EnvoGame game, String category, int screenId) {
        this.game = game;
        this.category = category;
        this.screenId = screenId;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        game.font.setColor(255, 255, 255, 1);
        ishide=false;

        String st= Gdx.files.local("maindata/"+category+"/names.data").readString();
        names = st.split(";");
        picture = new Texture(Gdx.files.local("maindata/"+category+"/pictures/"+(screenId+1)+".jpg"));
        read = Gdx.audio.newMusic(Gdx.files.local("maindata/"+category+"/english/"+(screenId+1)+".mp3"));
        rectLeft = new Rectangle(0,0,game.left.getWidth()+50,game.left.getHeight()+30);
        rectRight = new Rectangle(camera.viewportWidth-game.right.getWidth()-50,0,game.right.getWidth()+50,game.right.getHeight()+30);
        rectTop = new Rectangle(0,683,game.topbg.getWidth(),game.topbg.getHeight());
        rectBack = new Rectangle(rectTop.x,rectTop.y,game.backtop.getWidth()+50,game.backtop.getHeight()+30);
        rectSpeaker = new Rectangle(camera.viewportWidth-game.loudspeaker.getWidth()-70,rectTop.y,game.loudspeaker.getWidth()+50,game.loudspeaker.getHeight()+30);
        rectPicture = new Rectangle(15,125,picture.getWidth(),picture.getHeight());

        if (!game.isMute) read.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(picture, rectPicture.x, rectPicture.y);
        game.batch.draw(game.bottombg, 0, 0);
        game.batch.draw(game.topbg,rectTop.x,rectTop.y);
        game.batch.draw(game.backtop,rectBack.x+20,rectBack.y+5);
        game.batch.draw(game.isMute?game.mutespeaker:game.loudspeaker,rectSpeaker.x+40,rectSpeaker.y+5);
        game.batch.draw(game.left, rectLeft.x+10, rectLeft.y+5);
        game.batch.draw(game.right, rectRight.x+40, rectRight.y+5);
        GlyphLayout layout1 = new GlyphLayout(game.font,game.categoryNames[Integer.parseInt(category)-1]);
        game.font.draw(game.batch,layout1,camera.viewportWidth/2-layout1.width/2,rectTop.y + game.topbg.getHeight()/2+layout1.height/2);
        GlyphLayout layout = new GlyphLayout(game.font,names[screenId]);
        game.font.draw(game.batch,layout,camera.viewportWidth/2-layout.width/2,game.bottombg.getHeight()/2+layout.height/2);
        game.batch.end();

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
        picture.dispose();
        read.dispose();
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
        if (difX>50&&difX>difY)
        {
            int prevScreenId = screenId-1;
            if (prevScreenId>-1)
            this.game.setScreen(new GameScreen(game,category,prevScreenId));
        }
        //swipe left
        if (difX<-50&&difX<difY)
        {
            int nextScreenId = screenId+1;
            if (nextScreenId<names.length)
                this.game.setScreen(new GameScreen(game,category,nextScreenId));
        }
        if (difX<51&&difX>-51) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectBack,v.x,v.y)) game.setScreen(new MenuScreen(game,0));
            if (Helpers.isTouchedInRect(rectLeft,v.x,v.y))
            {
                int prevScreenId = screenId-1;
                if (prevScreenId>-1)
                    this.game.setScreen(new GameScreen(game,category,prevScreenId));
            }
            if (Helpers.isTouchedInRect(rectRight,v.x,v.y))
            {
                int nextScreenId = screenId+1;
                if (nextScreenId<names.length)
                    this.game.setScreen(new GameScreen(game,category,nextScreenId));
            }
            if (Helpers.isTouchedInRect(rectSpeaker,v.x,v.y))
            {
                if (game.isMute) game.isMute=false; else  game.isMute=true;
            }
            if (Helpers.isTouchedInRect(rectPicture,v.x,v.y))
            {
                if (!read.isPlaying()) read.play();
            }
        }
        return false;
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
