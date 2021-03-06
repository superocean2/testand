package com.samsam.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.samsam.snake.Model.Snake;
import com.samsam.snake.Model.SnakePart;
import com.samsam.snake.Model.Stain;
import com.samsam.snake.Model.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    Rectangle rectCancel;
    Rectangle rectSharefb;
    boolean isMute;
    int score;
    int oldscore;
    boolean isSubmit;

    public GameScreen(final SnakeGame game) {
        this.game=game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 420, 800);
        world=new World();
        rectLoudSpeaker=new Rectangle(45,640,game.loudSpeaker.getWidth(),game.loudSpeaker.getHeight());
        rectPause = new Rectangle(45+60,636,game.pause.getWidth(),game.pause.getHeight());
        rectLeft = new Rectangle(36,75,game.turnLeft.getWidth(),game.turnLeft.getHeight());
        rectRight= new Rectangle(265,75,game.turnRight.getWidth(),game.turnRight.getHeight());
        rectUp = new Rectangle(160,140,game.turnUp.getWidth(),game.turnUp.getHeight());
        rectDown = new Rectangle(160,35,game.turnDown.getWidth(),game.turnDown.getHeight());
        rectGameOver = new Rectangle(camera.viewportWidth/2-game.gameover.getWidth()/2,
                camera.viewportHeight/2-game.gameover.getHeight()/2 + game.worldScreen.y/2+45,game.gameover.getWidth(),game.gameover.getHeight());
        rectNewGame = new Rectangle(camera.viewportWidth/2-game.newgame.getWidth()/2,
                 rectGameOver.y-(22+game.newgame.getHeight()),game.newgame.getWidth(),game.newgame.getHeight());
        rectHighScore = new Rectangle(camera.viewportWidth/2-game.newgame.getWidth()/2,
                rectNewGame.y-(22+game.highscore.getHeight()),game.highscore.getWidth(),game.highscore.getHeight());

        rectResume = new Rectangle(camera.viewportWidth/2-game.resume.getWidth()/2,
                camera.viewportHeight/2-game.gameover.getHeight()/2 + game.worldScreen.y/2,
                game.resume.getWidth(),game.resume.getHeight());
        rectQuit = new Rectangle(camera.viewportWidth/2-game.quit.getWidth()/2,
                rectResume.y-(30+game.quit.getHeight()),
                game.quit.getWidth(),game.quit.getHeight());
        rectCancel = new Rectangle(155,80,game.cancel.getWidth(),game.cancel.getHeight());
        rectSharefb = new Rectangle(camera.viewportWidth/2 - game.sharefb.getWidth()/2,156,game.sharefb.getWidth(),game.sharefb.getHeight());
        score=0;
        oldscore=0;
        isMute=false;
        isSubmit=false;
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
        Snake snake = world.snake;
        SnakePart head = snake.parts.get(0);
        Stain stain = world.stain;

        float headX = head.x*20+game.worldScreen.x;
        float headY= head.y*20 + game.worldScreen.y;

        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        game.batch.draw(game.pause,rectPause.x,rectPause.y);
        game.batch.draw(isMute?game.muteSpeaker:game.loudSpeaker,rectLoudSpeaker.x,rectLoudSpeaker.y);
        game.batch.draw(game.turnLeft, rectLeft.x, rectLeft.y);
        game.batch.draw(game.turnRight, rectRight.x, rectRight.y);
        game.batch.draw(game.turnUp, rectUp.x, rectUp.y);
        game.batch.draw(game.turnDown, rectDown.x, rectDown.y);
        game.font.setColor(1, 1, 1, 1);
        GlyphLayout layout = new GlyphLayout(game.font, String.valueOf(score));
        game.font.draw(game.batch,layout, 200+(360-200)/2 - layout.width/2, 685);

        if (world.snake.direction==0) {
            game.batch.draw(game.snakeHeadUp, headX, headY);
        }
        if (world.snake.direction==1) {
            game.batch.draw(game.snakeHeadLeft, headX, headY);
        }
        if (world.snake.direction==2) {
            game.batch.draw(game.snakeHeadDown, headX, headY);
        }
        if (world.snake.direction==3) {
            game.batch.draw(game.snakeHeadRight, headX, headY);
        }

        for (int i=1;i<snake.parts.size();i++)
        {
            float x = snake.parts.get(i).x*20 + game.worldScreen.x;
            float y = snake.parts.get(i).y*20 + game.worldScreen.y;
            game.batch.draw(game.snakeTail,x,y);
        }
        if (stain.type==0)
        {
            game.batch.draw(game.food,stain.x*20+game.worldScreen.x,stain.y*20+game.worldScreen.y);
        }
        else
        {
            if (world.newStain) {
                if(!isMute) {
                    game.extraFoodSound.play();
                    world.newStain = false;
                }
            }
            game.batch.draw(game.exTraFood, stain.x * 20 + game.worldScreen.x, stain.y * 20 + game.worldScreen.y);
        }

        game.batch.end();
    }
    private void restartGame()
    {
        world=new World();
        score=0;
        oldscore=0;
        isSubmit=false;
    }

    private void updateReady()
    {
        if (Gdx.input.justTouched())
        {
            if (Gdx.input.justTouched()) {
                Vector3 v = new Vector3();
                v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(v);
                if (Helpers.isTouchedInRect(game.worldScreen, v.x, v.y)) {
                    state=GameState.Running;
                }
            }
        }

        drawWhiteRectangle();
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

            if (Helpers.isTouchedInRect(rectPause, v.x, v.y))
            {
                if (!isMute)
                state=GameState.Paused;
            }
        }
        world.update(Gdx.graphics.getDeltaTime());
        oldscore=score;
        score=world.score;

        if (oldscore!=score)
        {
            if (!isMute)
            game.eat.play();
        }
        if (world.gameOver)
        {
            if (!isMute)
            game.hit.play();
            state=GameState.GameOver;
        }
    }
    private void updatePaused()
    {
        drawWhiteRectangle();
        game.batch.begin();
        game.batch.draw(game.resume, rectResume.x, rectResume.y);
        game.batch.draw(game.quit, rectQuit.x, rectQuit.y);
        game.batch.end();
        if (Gdx.input.justTouched())
        {
            Vector3 v= new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectResume,v.x,v.y))
            {
                if (!isMute)
                state = GameState.Running;
            }
            if (Helpers.isTouchedInRect(rectQuit,v.x,v.y))
            {
                if (!isMute)
                state=GameState.GameOver;
            }
        }
    }
    private void updateGameOver()
    {
        //save score
        int highscore = Helpers.getHighScore();
        if (highscore<score)
        {
            Helpers.saveHighScore(score);
            if (!isSubmit) {
                state = GameState.SubmitWorldScore;
            }
        }

        drawWhiteRectangle();
        game.batch.begin();
        game.batch.draw(game.gameover, rectGameOver.x, rectGameOver.y);
        game.batch.draw(game.newgame, rectNewGame.x, rectNewGame.y);
        game.batch.draw(game.highscore, rectHighScore.x, rectHighScore.y);
        game.font.setColor(1, 0, 0, 1);
        GlyphLayout layout = new GlyphLayout(game.font,String.valueOf(score));
        game.font.draw(game.batch, layout, camera.viewportWidth / 2 - layout.width / 2, rectHighScore.y + 65);

        GlyphLayout layout1 = new GlyphLayout(game.font,String.valueOf(Helpers.getHighScore()));
        game.font.draw(game.batch,layout1,camera.viewportWidth/2-layout1.width/2,rectHighScore.y);
        game.batch.end();
        if (Gdx.input.justTouched())
        {
            Vector3 v=new Vector3();
            v.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectNewGame, v.x, v.y))
            {
                if (!isMute)
                state=GameState.Ready;
                restartGame();
            }
        }
    }
    private  void updateWorldScore()
    {
        game.batch.begin();
        game.batch.draw(game.worldScoreSubmit, 0, 0);
        game.batch.draw(game.cancel, rectCancel.x, rectCancel.y);
        game.batch.draw(game.sharefb,rectSharefb.x,rectSharefb.y);
        game.font.setColor(1, 0, 0, 1);
        GlyphLayout layout = new GlyphLayout(game.font,String.valueOf(score));
        game.font.draw(game.batch, layout, camera.viewportWidth / 2 - layout.width / 2, rectSharefb.y + 160);
        game.batch.end();
        if (Gdx.input.justTouched())
        {
            Vector3 v=new Vector3();
            v.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectCancel,v.x,v.y))
            {
                isSubmit=true;
                state=GameState.GameOver;
            }
            if (Helpers.isTouchedInRect(rectSharefb,v.x,v.y))
            {
                String title = "New high score in HISS SNAKE";
                String decs = "Your score: "+score +" . You are pro snake";
                String imageUrl = "http://2.bp.blogspot.com/-MLQhvppSUkk/VbhYYoW8FuI/AAAAAAAAPRY/V5fTFiIRQ2A/s1600/fbImage.png";
                String url = "https://play.google.com/store/apps/details?id=com.samsam.snake.android";
                game.actionResolver.showFbShare(title,decs,url,imageUrl);
                isSubmit=true;
                state=GameState.GameOver;
            }
        }
    }
    private void drawWhiteRectangle() {
        game.batch.begin();
        game.batch.draw(game.overlay,camera.viewportWidth/2-game.overlay.getWidth()/2,300);
        game.batch.end();
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
        GameOver,
        SubmitWorldScore
    }

}
