package com.samsam.newblock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by NghiaTrinh on 7/30/2015.
 */
public class GameScreen implements Screen {
    final  NewBlock game;
    OrthographicCamera camera;
    GameState state;
    World world;
    int score;
    int second;

    public GameScreen(NewBlock game) {
        this.game = game;
        state = GameState.Ready;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 410, 725);
        world=new World();
        score=0;
        second=5;
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
        game.font.setColor(1, 0, 0, 1);
        GlyphLayout layout1 = new GlyphLayout(game.font, String.valueOf(second));
        game.font.draw(game.batch, layout1, (int) (0.25 * camera.viewportWidth)-50, 620);
        game.batch.end();
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
        game.batch.begin();
        game.batch.draw(game.ready,camera.viewportWidth/2-game.ready.getWidth()/2,camera.viewportHeight/2-game.ready.getHeight()/2);
        game.batch.end();
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

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

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

    }

    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }
}
