package com.samsam.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
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
    Rectangle rectPause;
    Rectangle rectLoudSpeaker;
    Rectangle rectLeft;
    Rectangle rectRight;
    Rectangle rectUp;
    Rectangle rectDown;
    Rectangle rectNewGame;
    Rectangle rectGameOver;
    Rectangle rectHighScore;
    Rectangle rectResume;
    Rectangle rectQuit;
    boolean isMute;
    String score;

    public GameScreen(final SnakeGame game) {
        this.game=game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 420, 800);
        world=new World();
        rectPause = new Rectangle(53,643,game.pause.getWidth(),game.pause.getHeight());
        rectLoudSpeaker=new Rectangle(53+game.pause.getWidth()+10,643,game.loudSpeaker.getWidth(),game.loudSpeaker.getHeight());
        rectLeft = new Rectangle(128,110,game.turnLeft.getWidth(),game.turnLeft.getHeight());
        rectRight= new Rectangle(242,110,game.turnRight.getWidth(),game.turnRight.getHeight());
        rectUp = new Rectangle(181,170,game.turnUp.getWidth(),game.turnUp.getHeight());
        rectDown = new Rectangle(181,60,game.turnDown.getWidth(),game.turnDown.getHeight());
        rectGameOver = new Rectangle(camera.viewportWidth/2-game.gameover.getWidth()/2,
                camera.viewportHeight/2-game.gameover.getHeight()/2 + game.worldScreen.y/2+50,game.gameover.getWidth(),game.gameover.getHeight());
        rectNewGame = new Rectangle(camera.viewportWidth/2-game.newgame.getWidth()/2,
                 rectGameOver.y-(30+game.newgame.getHeight()),game.newgame.getWidth(),game.newgame.getHeight());
        rectHighScore = new Rectangle(camera.viewportWidth/2-game.newgame.getWidth()/2,
                rectNewGame.y-(30+game.highscore.getHeight()),game.highscore.getWidth(),game.highscore.getHeight());

        rectResume = new Rectangle(camera.viewportWidth/2-game.resume.getWidth()/2,
                camera.viewportHeight/2-game.gameover.getHeight()/2 + game.worldScreen.y/2,
                game.resume.getWidth(),game.resume.getHeight());
        rectQuit = new Rectangle(camera.viewportWidth/2-game.quit.getWidth()/2,
                rectResume.y-(30+game.quit.getHeight()),
                game.quit.getWidth(),game.quit.getHeight());
        score="0";
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
        SnakePart head = snake.parts.get(0);
        Stain stain = world.stain;

        float headX = head.x*10+game.worldScreen.x;
        float headY= head.y*10 + game.worldScreen.y;

        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        game.batch.draw(game.pause,rectPause.x,rectPause.y);
        game.batch.draw(game.loudSpeaker,rectLoudSpeaker.x,rectLoudSpeaker.y);
        game.batch.draw(game.turnLeft, rectLeft.x, rectLeft.y);
        game.batch.draw(game.turnRight, rectRight.x, rectRight.y);
        game.batch.draw(game.turnUp, rectUp.x, rectUp.y);
        game.batch.draw(game.turnDown, rectDown.x, rectDown.y);
        game.font.setColor(1, 1, 1, 1);
        game.font.draw(game.batch, score, 320, 685);

        game.batch.draw(game.snakeHead,headX,headY);
        for (int i=1;i<snake.parts.size();i++)
        {
            float x = snake.parts.get(i).x*10 + game.worldScreen.x;
            float y = snake.parts.get(i).y*10 + game.worldScreen.y;
            game.batch.draw(game.snakeTail,x,y);
        }
        if (stain.type==0)
        {
            game.batch.draw(game.food,stain.x*10+game.worldScreen.x,stain.y*10+game.worldScreen.y);
        }
        else
        {
            game.batch.draw(game.exTraFood,stain.x*10+game.worldScreen.x,stain.y*10+game.worldScreen.y);
        }

        game.batch.end();
    }
    private void restartGame()
    {
        world=new World();
        score="0";
    }

    private void updateReady()
    {
        if (Gdx.input.justTouched())
        {
            state=GameState.Running;
        }

        game.batch.begin();
        game.batch.draw(game.getReady, camera.viewportWidth / 2 - game.getReady.getWidth() / 2,
                camera.viewportHeight / 2 - game.getReady.getHeight() / 2 + game.worldScreen.y / 2);
        game.batch.end();
    }
    private void updateRunning()
    {
        if (Gdx.input.justTouched())
        {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectLeft,v.x,v.y)) {
                world.snake.turnLeft();
            }
            if (Helpers.isTouchedInRect(rectRight,v.x,v.y)) {
                world.snake.turnRight();
            }
            if (Helpers.isTouchedInRect(rectUp,v.x,v.y)) {
                world.snake.turnUp();
            }
            if (Helpers.isTouchedInRect(rectDown,v.x,v.y)) {
                world.snake.turnDown();
            }

            if (Helpers.isTouchedInRect(rectPause,v.x,v.y))
            {
                state=GameState.Paused;
            }
        }
        world.update(Gdx.graphics.getDeltaTime());
        score=String.valueOf(world.score);
        if (world.gameOver)
        {
            state=GameState.GameOver;
        }
    }
    private void updatePaused()
    {
        game.batch.begin();
        game.batch.draw(game.resume,rectResume.x,rectResume.y);
        game.batch.draw(game.quit,rectQuit.x,rectQuit.y);
        game.batch.end();
        if (Gdx.input.justTouched())
        {
            Vector3 v= new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectResume,v.x,v.y))
            {
                state = GameState.Running;
            }
            if (Helpers.isTouchedInRect(rectQuit,v.x,v.y))
            {
                state=GameState.GameOver;
            }
        }
    }
    private void updateGameOver()
    {
        game.batch.begin();
        game.batch.draw(game.gameover, rectGameOver.x, rectGameOver.y);
        game.batch.draw(game.newgame, rectNewGame.x, rectNewGame.y);
        game.batch.draw(game.highscore, rectHighScore.x, rectHighScore.y);
        game.font.setColor(1, 0, 0, 1);
        GlyphLayout layout = new GlyphLayout(game.font,score);
        game.font.draw(game.batch,layout,camera.viewportWidth/2-layout.width/2,rectHighScore.y+67);
        game.batch.end();
        if (Gdx.input.justTouched())
        {
            Vector3 v=new Vector3();
            v.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectNewGame,v.x,v.y))
            {
                state=GameState.Ready;
                restartGame();
            }
        }
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
