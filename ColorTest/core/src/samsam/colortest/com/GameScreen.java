package samsam.colortest.com;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NghiaTrinh on 7/6/2015.
 */
public class GameScreen implements Screen{
    final ColorTest game;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    Texture bgScore;
    Texture bgTimeleft;
    Music tiktok;
    Sound gameOver;
    Sound hit;

    int score;
    int level;
    long startTime;
    int timeLeft;
    Rectangle theChoice;
    Rectangle rectGame;
    int space=5;

    int sizes;
    int choiceRect;
    int cdiff;
    int r;
    int g;
    int b;

    public  GameScreen(final ColorTest test)
    {
        game = test;
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        bgScore=new Texture("score_bg.png");
        bgTimeleft=new Texture("timeleft_bg.png");
        tiktok = Gdx.audio.newMusic(Gdx.files.internal("sound/tiktok.mp3"));
        gameOver = Gdx.audio.newSound(Gdx.files.internal("sound/wrong.mp3"));
        hit = Gdx.audio.newSound(Gdx.files.internal("sound/correct.mp3"));
        rectGame = new Rectangle(40, 200, 400, 400);
        resetGame();
    }

    private void resetGame()
    {
        score=0;
        level=1;
        startTime=0;
        timeLeft=15;
        sizes = getCols(level);
        choiceRect = Helpers.randomInt(0, sizes * sizes);
        cdiff = colorDiff(level);
        r = (int)Math.floor(Math.random() *(255-cdiff));
        g = (int)Math.floor(Math.random() *(255-cdiff));
        b = (int)Math.floor(Math.random() *(255-cdiff));
    }
    private  void nextLevel()
    {
        sizes = getCols(level);
        choiceRect = Helpers.randomInt(0, sizes * sizes);
        cdiff = colorDiff(level);
        r = (int)Math.floor(Math.random() *(255-cdiff));
        g = (int)Math.floor(Math.random() *(255-cdiff));
        b = (int)Math.floor(Math.random() *(255-cdiff));
    }
    private int colorDiff(int l)
    {
        if (l<44) {
            int[] col = {75, 60, 45, 30, 20, 18, 16, 15, 14, 13, 12, 11, 11, 11, 10, 10, 9, 9, 8, 8, 7, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 5, 4, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 2};
            return col[l];
        }
        return 1;
    }
    private int getCols(int l)
    {
        if (l < 2) return 2;
        if (l < 4) return 3;
        if (l < 6) return 4;
        if (l < 10) return 5;
        if (l < 16) return 6;
        if (l < 20) return 7;
        return 8;
    }
    private void updateScore()
    {
        score=level-1;

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //update timeleft
        if (startTime>0)
        {
            if (TimeUtils.timeSinceNanos(startTime)>1000000000)
            {
                timeLeft--;
                if (timeLeft<0) timeLeft=0;
                startTime=TimeUtils.nanoTime();
            }
        }
        if (timeLeft==0&&tiktok.isPlaying())
        {
            tiktok.stop();
            game.setScreen(new Result(game,score));
        }
        if (timeLeft==5)
        {
            if (!tiktok.isPlaying()) {
                tiktok.play();
            }
            game.fontTimeleft.setColor(250/255f,230/255f,45/255f,1);
        }
        else
        {
            game.fontTimeleft.setColor(255,255,255,1);
        }
        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        game.batch.draw(bgScore,50,800-bgScore.getHeight()-20);
        game.batch.draw(bgTimeleft,400-100,800-bgScore.getHeight()-20);
        if (score>9) {
            game.fontScore.draw(game.batch, String.valueOf(score), 100, 800 - 75);
        }
        else
        {
            game.fontScore.draw(game.batch, String.valueOf(score), 110, 800 - 75);
        }
        if (timeLeft>9) {
            game.fontTimeleft.draw(game.batch, String.valueOf(timeLeft), 400 - 70, 800 - 75);
        }
        else
        {
            game.fontTimeleft.draw(game.batch, String.valueOf(timeLeft), 400 - 57, 800 - 75);
        }
        game.batch.end();



        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(255, 255, 255, 1);
        shapeRenderer.rect(rectGame.x, rectGame.y,rectGame.width, rectGame.height);
        //render cols
        List<Rectangle> list = getColsPosition();

        shapeRenderer.setColor(r/255f,g/255f,b/255f, 1);
        for(int i=0;i<list.size();i++)
        {
            Rectangle rect = list.get(i);
            if (i==choiceRect)
            {
                shapeRenderer.setColor((r+cdiff)/255f,(g+cdiff)/255f,(b+cdiff)/255f, 1);
                theChoice=rect;
            }
            else
            {
                shapeRenderer.setColor(r/255f,g/255f,b/255f, 1);
            }
            shapeRenderer.rect(rect.x,rect.y,rect.width,rect.height);
        }

        shapeRenderer.end();

        //detect tap on the choice
        if (Gdx.input.justTouched())
        {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(rectGame,v.x,v.y)) {
                if (Helpers.isTouchedInRect(theChoice, v.x, v.y)) {
                    hit.play();
                    startTime = TimeUtils.nanoTime();
                    timeLeft = 15;
                    level++;
                    nextLevel();
                    updateScore();
                    if (tiktok.isPlaying()) {
                        tiktok.stop();
                    }
                } else {
                    if (startTime > 0) {
                        gameOver.play(0.5f);
                        game.setScreen(new Result(game, score));
                    }
                }
            }
        }
    }
    private List<Rectangle> getColsPosition()
    {
        List<Rectangle> rectangles = new ArrayList<Rectangle>();
        int sizes = getCols(level);
        int spaces = (sizes-1)*space;
        float w = (400-spaces)/sizes;
        int paddingY = 200;
        int paddingX = 40;

        for (int i=0;i<sizes;i++)
        {
            for (int j=0;j<sizes;j++)
            {
                float x = paddingX+ i*w + i*space;
                float y = paddingY+ j*w + j*space;
                rectangles.add(new Rectangle(x,y,w,w));
            }
        }
        return rectangles;
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

    }

    @Override
    public void dispose() {
        game.dispose();
        shapeRenderer.dispose();
        bgScore.dispose();
        bgTimeleft.dispose();
        tiktok.dispose();
        gameOver.dispose();
        hit.dispose();
    }
}
