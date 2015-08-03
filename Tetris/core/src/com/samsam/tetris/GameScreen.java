package com.samsam.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.samsam.tetris.Model.World;

import java.util.Timer;

/**
 * Created by NghiaTrinh on 7/30/2015.
 */
public class GameScreen implements Screen {
    final  TetrisGame game;
    OrthographicCamera camera;
    GameState state;
    Rectangle rectPause;
    Rectangle rectLoudSpeaker;
    Rectangle rectLeft;
    Rectangle rectRight;
    Rectangle rectDown;
    Rectangle rectRotate;
    boolean isMute;
    World world;


    public GameScreen(TetrisGame game) {
        this.game = game;
        state = GameState.Ready;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 420, 800);
        rectPause = new Rectangle(320,540,game.pause.getWidth(),game.pause.getHeight());
        rectLoudSpeaker = new Rectangle(320,540+game.pause.getHeight()+10,game.loudSpeaker.getWidth(),game.loudSpeaker.getHeight());
        rectDown = new Rectangle(0,0,game.down.getWidth(),game.down.getHeight());
        rectLeft = new Rectangle(game.down.getWidth()-5,0,game.left.getWidth(),game.left.getHeight());
        rectRight = new Rectangle(rectLeft.x+ game.left.getWidth()-3,0,game.right.getWidth(),game.right.getHeight());
        rectRotate = new Rectangle(rectRight.x+ game.right.getWidth()-3,0,game.rotate.getWidth(),game.rotate.getHeight());
        isMute=false;
        world=new World();
        world.update();
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
        if(state == GameState.SubmitWorldScore)
            updateWorldScore();

        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectLoudSpeaker, v.x, v.y)) {
                if (isMute) isMute=false;
                else isMute=true;
            }
        }
    }

    private void drawWorld()
    {
        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        game.batch.draw(game.loudSpeaker, rectLoudSpeaker.x, rectLoudSpeaker.y);
        game.batch.draw(game.pause, rectPause.x, rectPause.y);
        game.batch.draw(game.down, rectDown.x, rectDown.y);
        game.batch.draw(game.left, rectLeft.x, rectLeft.y);
        game.batch.draw(game.right, rectRight.x, rectRight.y);
        game.batch.draw(game.rotate,rectRotate.x,rectRotate.y);
        for (int i=4;i< world.gridcols +4;i++)
            for (int j=4;j< world.gridrows +4;j++)
                if (world.pool[i][j]!=0)
                {
                    game.batch.draw(game.block,(i-4)*game.mainBlockWidth+game.rectScreen.x+game.rectScreen.width/2, game.rectScreen.y+ game.rectScreen.getHeight()-(j-3)*game.mainBlockHeight);
                }
            for (int i=0;i<4;i++)
            {
                float x = game.rectScreen.x  + (world.main_figure.data[i][0] + world.main_figure.x - 4) * game.mainBlockWidth;
                float y = game.rectScreen.y +(world.main_figure.data[i][1] - 3 + world.main_figure.y) * game.mainBlockHeight-80;
                if (game.rectScreen.x<x&&x<game.rectScreen.x+game.rectScreen.width
                        &&game.rectScreen.y<y&&y<game.rectScreen.y+game.rectScreen.height) {
                    game.batch.draw(game.block, x, y, game.block.getWidth(), game.block.getHeight());
                }
            }
        game.batch.end();
    }

    private void updateReady()
    {

    }

    public void updateRunning()
    {

    }

    private void updatePaused()
    {

    }

    private void updateGameOver()
    {

    }

    private void updateWorldScore()
    {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

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

    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver,
        SubmitWorldScore
    }
}
