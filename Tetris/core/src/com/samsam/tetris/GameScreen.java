package com.samsam.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.samsam.tetris.Model.World;


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
    //Rectangle rectDown;
    Rectangle rectRotate;
    Rectangle rectResume;
    Rectangle rectQuit;
    Rectangle rectNewgame;
    Rectangle rectGameover;
    Rectangle rectHighscore;
    Rectangle rectShareFb;
    Rectangle rectCancel;
    boolean isMute;
    World world;
    int score;
    boolean gameIsStart;
    boolean isSubmit;
    int oldscore;


    public GameScreen(TetrisGame game) {
        this.game = game;
        state = GameState.Ready;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 420, 800);
        rectPause = new Rectangle(330,540,game.pause.getWidth(),game.pause.getHeight());
        rectLoudSpeaker = new Rectangle(330,540+game.pause.getHeight()+10,game.loudSpeaker.getWidth(),game.loudSpeaker.getHeight());
        //rectDown = new Rectangle(0,0,game.down.getWidth(),game.down.getHeight());
        rectLeft = new Rectangle(0,0,game.left.getWidth(),game.left.getHeight());
        rectRight = new Rectangle(rectLeft.x+ game.left.getWidth()+2,0,game.right.getWidth(),game.right.getHeight());
        rectRotate = new Rectangle(rectRight.x+ game.right.getWidth()+5,0,game.rotate.getWidth(),game.rotate.getHeight());
        rectResume = new Rectangle(game.rectScreen.width / 2 - game.resume.getWidth() / 2+7,440,game.resume.getWidth(),game.resume.getHeight());
        rectQuit = new Rectangle(game.rectScreen.width / 2 - game.quit.getWidth() / 2+7,rectResume.y-80,game.quit.getWidth(),game.quit.getHeight());
        rectGameover =new Rectangle(game.rectScreen.width / 2 - game.gameOver.getWidth() / 2 + 7,540,game.gameOver.getWidth(),game.gameOver.getHeight());
        rectNewgame = new Rectangle(game.rectScreen.width / 2 - game.newGame.getWidth() / 2 + 7,rectGameover.y-game.newGame.getHeight()-20,game.newGame.getWidth(),game.newGame.getHeight());
        rectHighscore = new Rectangle(game.rectScreen.width / 2 - game.highScore.getWidth() / 2 + 7,rectNewgame.y-game.highScore.getHeight()-20,game.highScore.getWidth(),game.highScore.getHeight());
        rectShareFb = new Rectangle(game.rectScreen.width / 2 - game.highScore.getWidth() / 2 + 7,350,game.shareFb.getWidth(),game.shareFb.getHeight());
        rectCancel = new Rectangle(rectShareFb.x,rectShareFb.y-game.cancel.getHeight()-20,game.cancel.getWidth(),game.cancel.getHeight());
        isMute=false;
        world=new World();
        score=0;
        gameIsStart=false;
        isSubmit=false;
        oldscore=0;
        if (!isMute)
        game.bacgroundMusic.play();
        game.bacgroundMusic.setLooping(true);
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

    }

    private void drawWorld()
    {
        Vector3 v= new Vector3();
        v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(v);
        if (Gdx.input.justTouched()) {
            if (Helpers.isTouchedInRect(rectLoudSpeaker, v.x, v.y)) {
                if (isMute)
                {
                    isMute=false;
                    game.bacgroundMusic.play();
                }
                else
                {
                    isMute=true;
                    if (game.bacgroundMusic.isPlaying())
                    {
                        game.bacgroundMusic.stop();
                    }
                }
            }
        }

        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        game.batch.draw(isMute ? game.muteSpeaker : game.loudSpeaker, rectLoudSpeaker.x, rectLoudSpeaker.y);
        game.batch.draw(game.pause, rectPause.x, rectPause.y);
       // game.batch.draw(game.down, rectDown.x, rectDown.y);
        game.batch.draw(game.left, rectLeft.x, rectLeft.y);
        game.batch.draw(game.right, rectRight.x, rectRight.y);
        game.batch.draw(game.rotate, rectRotate.x, rectRotate.y);
        game.font.setColor(1, 1, 1, 1);
        GlyphLayout layout = new GlyphLayout(game.font, String.valueOf(score));
        game.font.draw(game.batch,layout, 310+(420-310)/2 - layout.width/2, 332);

        for (int i=0;i< world.gridcols+4;i++)
            for (int j=1;j< world.gridrows+4;j++)
                if (world.pool[i][j]!=0)
                {
                    float x = game.rectScreen.x  + (i-1)*game.mainBlockWidth;
                    float y = game.rectScreen.y + (j-1)*game.mainBlockHeight;
                    if (game.rectScreen.x<x+1&&x<game.rectScreen.x+game.rectScreen.width
                            &&y<game.rectScreen.y+game.rectScreen.height) {
                        game.batch.draw(game.block, x, y);
                    }
                }
        for (int i=0;i<4;i++)
        {
            float x = game.rectScreen.x  + (world.main_figure.x-1)*game.mainBlockWidth + world.main_figure.data[i][0]*game.mainBlockWidth;
            float y = game.rectScreen.y + world.main_figure.y*game.mainBlockHeight + world.main_figure.data[i][1]*game.mainBlockHeight;

            if (game.rectScreen.x<x+1&&x<game.rectScreen.x+game.rectScreen.width
                    &&game.rectScreen.y<y&&y<game.rectScreen.y+game.rectScreen.height) {
                game.batch.draw(game.block, x, y, game.block.getWidth(), game.block.getHeight());
            }
        }

        //render block mini
        for (int i=0;i<4;i++)
        {
            float x = 318 + world.next_figure.data[i][0]*game.blockmini.getWidth();
            float y = 403 + world.next_figure.data[i][1]*game.blockmini.getWidth();
            game.batch.draw(game.blockmini, x, y, game.blockmini.getWidth(), game.blockmini.getHeight());
        }
        game.batch.end();


    }

    private  void restartGame()
    {
        world.restart();
        world = new World();
        score=0;
        state=GameState.Ready;
        gameIsStart=false;
        isSubmit=false;
    }
    private void updateReady()
    {
        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);

            if (Helpers.isTouchedInRect(game.rectScreen, v.x, v.y)) {
                state = GameState.Running;
            }
        }
        game.batch.begin();
        drawOverlayBg();
        game.batch.draw(game.hissBrick,game.rectScreen.width / 2 - game.hissBrick.getWidth() / 2 + 7,500);
        game.batch.draw(game.ready, game.rectScreen.width / 2 - game.ready.getWidth() / 2+7, 400);
        game.batch.end();
    }

    public void updateRunning()
    {
        if (!gameIsStart) {
            world.update();
            gameIsStart=true;
        }
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
            if (Helpers.isTouchedInRect(rectRotate, v.x, v.y)) {
                world.main_figure.rotate(world.pool);
            }
//            if (Helpers.isTouchedInRect(rectDown, v.x, v.y)) {
//                world.main_figure.drop(world.pool);
//            }

            if (Helpers.isTouchedInRect(rectPause, v.x, v.y)) {
                world.pause();
                state = GameState.Paused;
            }
        }

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
        game.batch.begin();
        drawOverlayBg();
        game.batch.draw(game.hissBrick,game.rectScreen.width / 2 - game.hissBrick.getWidth() / 2 + 7,540);
        game.batch.draw(game.resume, rectResume.x, rectResume.y);
        game.batch.draw(game.quit, rectQuit.x, rectQuit.y);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectResume, v.x, v.y)) {
                world.resume();
                state=GameState.Running;
            }
            if (Helpers.isTouchedInRect(rectQuit, v.x, v.y)) {
                world.game_Over();
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
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    state = GameState.SubmitWorldScore;
                }
            },0.2f);
        }

        game.batch.begin();
        drawOverlayBg();
        game.batch.draw(game.gameOver, rectGameover.x, rectGameover.y);
        game.batch.draw(game.newGame, rectNewgame.x, rectNewgame.y);
        game.batch.draw(game.highScore,rectHighscore.x,rectHighscore.y);

        //game.font.setColor(1, 0, 0, 1);
        GlyphLayout layout = new GlyphLayout(game.font,String.valueOf(score));
        game.font.draw(game.batch, layout, game.rectScreen.width / 2 - layout.width / 2 +7, rectHighscore.y + 65);

        GlyphLayout layout1 = new GlyphLayout(game.font,String.valueOf(Helpers.getHighScore()));
        game.font.draw(game.batch,layout1,game.rectScreen.width/2-layout1.width/2 +7,rectHighscore.y+10);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Gdx.input.justTouched()) {
                if (Helpers.isTouchedInRect(rectNewgame, v.x, v.y)) {
                    restartGame();
                }
            }
        }
    }

    private void updateWorldScore()
    {
        game.batch.begin();
        game.batch.draw(game.worldScoreBg, game.rectScreen.width / 2 - game.overlay.getWidth() / 2 + 7, 246);
        GlyphLayout layout = new GlyphLayout(game.font,String.valueOf(score));
        game.font.draw(game.batch, layout, game.rectScreen.width / 2 - layout.width / 2 +7, 443);
        game.batch.draw(game.shareFb,rectShareFb.x,rectShareFb.y);
        game.batch.draw(game.cancel,rectCancel.x,rectCancel.y);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Gdx.input.justTouched()) {
                if (Helpers.isTouchedInRect(rectShareFb, v.x, v.y)) {
                    game.actionResolver.showFbShare("Your score: "+String.valueOf(score)+"","Good job!","funnynet.net","http://2.bp.blogspot.com/-2ODs7CtgtQ0/Vcih5pPbqOI/AAAAAAAAPRs/e0bjvlMuFDE/s1600/fbsharebg.png");
                    state= GameState.GameOver;
                }
                if (Helpers.isTouchedInRect(rectCancel,v.x,v.y))
                {
                    state = GameState.GameOver;
                }
            }
        }
    }

    private void drawOverlayBg()
    {
        game.batch.draw(game.overlay,game.rectScreen.width/2-game.overlay.getWidth()/2+7,246);
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
