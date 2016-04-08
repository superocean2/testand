package com.samsam.happybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by NghiaTrinh on 7/30/2015.
 */
public class GameScreen implements Screen {
    final HappyBird game;
    OrthographicCamera camera;
    GameState state;
    World world;
    int score, highscore;
    boolean gameIsStart, isSaveHighscore;

    Rectangle rectPause;
    Rectangle rectRestart;
    Rectangle rectRateGame;

    public GameScreen(HappyBird game) {
        this.game = game;
        state = GameState.Ready;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 790, 420);
        world = new World();
        world.bird.image = game.bird;

        score = 0;
        gameIsStart = false;
    }

    private void DisplayFence(float x, float y, int h)
    {
        float yLineBottom = y;
        float yCol = yLineBottom + game.fenceLine.getRegionHeight()-1;
        float yLineTop = yCol + h;
        game.batch.draw(game.fenceLine, x, yLineBottom);
        game.batch.draw(new TextureRegion(game.fenceCol,0,0,game.fenceCol.getRegionWidth(),h),x,yCol);
        game.batch.draw(game.fenceLine,x,yLineTop);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        drawWorld();

        if (state == GameState.Ready)
            updateReady();
        if (state == GameState.Running)
            updateRunning();
        if (state == GameState.Paused)
            updatePaused();
        if (state == GameState.GameOver)
            updateGameOver();
    }

    private void drawWorld() {
        Vector3 v = new Vector3();
        v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(v);
        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        for (Fence fence:world.fences) {
            DisplayFence(fence.position.x,fence.position.y,fence.height);
        }
        //game.batch.draw(world.bird.image,world.bird.position.x,world.bird.position.y);
        game.batch.end();

    }



    private void restartGame() {
        world = new World();
        score = 0;
        gameIsStart = false;
        isSaveHighscore = false;
        state = GameState.Ready;
    }

    private void updateReady() {
        if (Gdx.input.justTouched()) {
            state = GameState.Running;
        }
        game.batch.begin();
        //game.batch.draw(game.ready, camera.viewportWidth / 2 - game.ready.getWidth() / 2, camera.viewportHeight / 2 - game.ready.getHeight() / 2);
        game.batch.end();

    }

    public void updateRunning() {
        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
        }
    }

    private void updatePaused() {
        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
        }
        game.batch.begin();
        //game.batch.draw(game.resume, camera.viewportWidth / 2 - game.resume.getWidth() / 2, camera.viewportHeight / 2 - game.resume.getHeight() / 2);
        game.batch.end();
    }

    private void updateGameOver() {
        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);

            if (Helpers.isTouchedInRect(rectRestart, v.x, v.y)) {
                restartGame();
            }
            if (Helpers.isTouchedInRect(rectRateGame, v.x, v.y)) {
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.samsam.newblock");
            }
        }

        game.batch.begin();

        GlyphLayout layout = new GlyphLayout(game.font, String.valueOf(highscore));



        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
        state = GameState.Paused;
    }

    @Override
    public void resume() {
        state = GameState.Running;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    enum GameState {
        Instruction,
        Ready,
        Running,
        Paused,
        GameOver
    }
}
