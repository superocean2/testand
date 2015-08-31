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

public class MenuScreen implements Screen{
    final ColorGame game;
    OrthographicCamera camera;
    Texture red;
    Texture green;
    Texture blue;
    Texture yellow;
    Texture pink;
    Texture orange;
    Texture violet;
    Texture black;
    Texture brown;
    Texture white;
    Rectangle rectRed;
    Rectangle rectGreen;
    Rectangle rectBlue;
    Rectangle rectYellow;
    Rectangle rectPink;
    Rectangle rectOrange;
    Rectangle rectViolet;
    Rectangle rectBlack;
    Rectangle rectBrown;
    Rectangle rectWhite;
    boolean ishide;

    public MenuScreen(ColorGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 1280);
        game.font.setColor(255,0,0,1);
        ishide=false;

        red = new Texture("red.png");
        green=new Texture("green.png");
        blue=new Texture("blue.png");
        yellow = new Texture("yellow.png");
        pink = new Texture("pink.png");
        orange = new Texture("orange.png");
        violet = new Texture("violet.png");
        black = new Texture("black.png");
        brown = new Texture("brown.png");
        white = new Texture("white.png");
        rectBrown = new Rectangle(50,50,brown.getWidth(),brown.getHeight());
        rectBlack = new Rectangle(rectBrown.x,rectBrown.y+rectBrown.height+50,black.getWidth(),black.getHeight());
        rectPink = new Rectangle(rectBrown.x,rectBlack.y+rectBlack.height+50,pink.getWidth(),pink.getHeight());
        rectYellow = new Rectangle(rectBrown.x,rectPink.y+rectPink.height+50,yellow.getWidth(),yellow.getHeight());
        rectRed = new Rectangle(rectBrown.x,rectYellow.y+rectYellow.height+50,red.getWidth(),red.getHeight());

        rectWhite = new Rectangle(450,rectBrown.y,white.getWidth(),white.getHeight());
        rectOrange = new Rectangle(rectWhite.x,rectWhite.y+rectWhite.height+50,orange.getWidth(),orange.getHeight());
        rectViolet = new Rectangle(rectWhite.x,rectOrange.y+rectOrange.height+50,violet.getWidth(),violet.getHeight());
        rectBlue = new Rectangle(rectWhite.x,rectViolet.y+rectViolet.height+50,blue.getWidth(),blue.getHeight());
        rectGreen = new Rectangle(rectWhite.x,rectBlue.y+rectBlue.height+50,green.getWidth(),green.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(brown, rectBrown.x, rectBrown.y);
        game.batch.draw(black, rectBlack.x, rectBlack.y);
        game.batch.draw(pink,rectPink.x,rectPink.y);
        game.batch.draw(yellow,rectYellow.x,rectYellow.y);
        game.batch.draw(red,rectRed.x,rectRed.y);
        game.batch.draw(white, rectWhite.x, rectWhite.y);
        game.batch.draw(orange, rectOrange.x, rectOrange.y);
        game.batch.draw(violet, rectViolet.x, rectViolet.y);
        game.batch.draw(blue,rectBlue.x,rectBlue.y);
        game.batch.draw(green, rectGreen.x, rectGreen.y);
        GlyphLayout layout = new GlyphLayout(game.font,"Pick color");
        game.font.draw(game.batch,layout,camera.viewportWidth/2-layout.width/2,camera.viewportHeight-150);
        game.batch.end();

        if (Gdx.input.justTouched())
        {
            Vector3 v= new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectBlack,v.x,v.y))
            {
                game.setScreen(new GameScreen(game,"Black",1));
            }
            if (Helpers.isTouchedInRect(rectBlue,v.x,v.y))
            {
                game.setScreen(new GameScreen(game,"Blue",1));
            }
            if (Helpers.isTouchedInRect(rectBrown,v.x,v.y))
            {
                game.setScreen(new GameScreen(game,"Brown",1));
            }
            if (Helpers.isTouchedInRect(rectGreen,v.x,v.y))
            {
                game.setScreen(new GameScreen(game,"Green",1));
            }
            if (Helpers.isTouchedInRect(rectOrange,v.x,v.y))
            {
                game.setScreen(new GameScreen(game,"Orange",1));
            }
            if (Helpers.isTouchedInRect(rectPink,v.x,v.y))
            {
                game.setScreen(new GameScreen(game,"Pink",1));
            }
            if (Helpers.isTouchedInRect(rectRed,v.x,v.y))
            {
                game.setScreen(new GameScreen(game,"Red",1));
            }
            if (Helpers.isTouchedInRect(rectViolet,v.x,v.y))
            {
                game.setScreen(new GameScreen(game,"Violet",1));
            }
            if (Helpers.isTouchedInRect(rectYellow,v.x,v.y))
            {
                game.setScreen(new GameScreen(game,"Yellow",1));
            }
            if (Helpers.isTouchedInRect(rectWhite,v.x,v.y))
            {
                game.setScreen(new GameScreen(game,"White",1));
            }
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
        red.dispose();
        green.dispose();
        blue.dispose();
        yellow.dispose();
        pink.dispose();
        orange.dispose();
        violet.dispose();
        black.dispose();
        white.dispose();
        brown.dispose();
    }
}
