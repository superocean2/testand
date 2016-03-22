package com.samsam.newblock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import org.w3c.dom.css.Rect;


/**
 * Created by NghiaTrinh on 7/30/2015.
 */
public class GameScreen implements Screen,InputProcessor {
    final  NewBlock game;
    OrthographicCamera camera;
    GameState state;
    World world;
    int score;
    int xDown,yDown,xUp,yUp;
    boolean gameIsStart;
    Rectangle rectLeft;
    Rectangle rectRight;

    public GameScreen(NewBlock game) {
        this.game = game;
        state = GameState.Ready;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 410, 725);
        world=new World();
        score=0;
        gameIsStart=false;
        rectLeft = new Rectangle(0,0,game.rectScreen.width/2,game.rectScreen.height);
        rectRight = new Rectangle(game.rectScreen.width/2,0,game.rectScreen.width/2,game.rectScreen.height);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        drawWorld();

        if(state == GameState.Ready)
            updateReady();
        if(state == GameState.Running)
            updateRunning();
        if(state == GameState.Paused)
            updatePaused();
        if(state == GameState.GameOver)
            updateGameOver();
    }

    private void drawWorld()
    {
        Vector3 v= new Vector3();
        v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(v);
        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        game.font.setColor(0, 0, 0, 1);
        GlyphLayout layout = new GlyphLayout(game.font, String.valueOf(score));
        game.font.draw(game.batch, layout, (int)(0.75*camera.viewportWidth)+30-layout.width/2, 620);

        //draw wait block
        for(int j=0;j<world.main_figure.data.length;j++)
        {
            int x = (world.main_figure.x+world.main_figure.data[j][0])*game.blockWidth;
            int y = (world.main_figure.y+world.main_figure.data[j][1])*game.blockHeight;
            game.batch.draw(getTextureColor(world.main_figure.color),x,y);
        }
        //draw current block
        game.batch.end();

    }
    private TextureRegion getTextureColor(int color)
    {
        switch (color)
        {
            case 0:
                return new TextureRegion(game.block,0,0,30,30);
            case 1:
                return new TextureRegion(game.block,30,0,30,30);
            case 2:
                return new TextureRegion(game.block,60,0,30,30);
            case 3:
                return new TextureRegion(game.block,90,0,30,30);
            case 4:
                return new TextureRegion(game.block,0,30,30,30);
            case 5:
                return new TextureRegion(game.block,30,30,30,30);
            case 6:
                return new TextureRegion(game.block,60,30,30,30);
            case 7:
                return new TextureRegion(game.block,90,30,30,30);
            default:
                return new TextureRegion(game.block,0,0,30,30);
        }
    }
    private  void restartGame()
    {
        world.restart();
        world = new World();
        score=0;
        state= GameState.Ready;
    }
    private void updateReady()
    {
        if(Gdx.input.justTouched())
        {
            state= GameState.Running;
        }
        game.batch.begin();
        game.batch.draw(game.ready,camera.viewportWidth/2-game.ready.getWidth()/2,camera.viewportHeight/2-game.ready.getHeight()/2);
        game.batch.end();

    }

    public void updateRunning()
    {
        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectLeft, v.x, v.y)) {
                world.main_figure.move_left(world.pool);
            }
            if (Helpers.isTouchedInRect(rectRight, v.x, v.y)) {
                world.main_figure.move_right(world.pool);
            }
        }
        if (!gameIsStart) {
            world.update();
            gameIsStart=true;
        }
    }

    private void updatePaused()
    {

    }

    private void updateGameOver()
    {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void pause() {
        world.pause();
        state = GameState.Paused;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

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
        if (difX>20&&difX>difY)
        {

        }
        //swipe left
        if (difX<-20&&difX<difY)
        {

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
    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }
}
