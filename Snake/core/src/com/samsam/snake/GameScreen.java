package com.samsam.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.samsam.snake.Model.Snake;
import com.samsam.snake.Model.SnakePart;
import com.samsam.snake.Model.Stain;
import com.samsam.snake.Model.World;

/**
 * Created by NghiaTrinh on 7/23/2015.
 */
public class GameScreen implements Screen{

    final SnakeGame game;
    OrthographicCamera camera;
    GameState state = GameState.Ready;
    World world;

    public GameScreen(final SnakeGame game) {
        this.game=game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 420, 800);
        world=new World();
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
        Snake snake = world.snake;
        SnakePart head = snake.parts.get(snake.parts.size()-1);
        Stain stain = world.stain;

        float headX = camera.viewportWidth/2-game.snakeHead.getWidth()/2;
        float headY= head.y*10+20 + game.GAME_SCREEN_PADDING_BOTTOM;

        game.batch.begin();
        game.batch.draw(game.background,0,0);
        game.batch.draw(game.snakeHead,headX,headY);
        for (int i=0;i<snake.parts.size()-2;i++)
        {
            float x = camera.viewportWidth/2-game.snakeHead.getWidth()/2;
            float y = snake.parts.get(i).y*10+20+game.GAME_SCREEN_PADDING_BOTTOM;
            game.batch.draw(game.snakeTail,x,y);
        }
        game.batch.end();
    }
    private void updateReady()
    {
        if (Gdx.input.justTouched())
        {
            state=GameState.Running;
        }
    }
    private void updateRunning()
    {

    }
    private void updatePaused()
    {

    }
    private void updateGameOver()
    {

    }


    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

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
