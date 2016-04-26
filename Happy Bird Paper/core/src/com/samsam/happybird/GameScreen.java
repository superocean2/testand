package com.samsam.happybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


/**
 * Created by NghiaTrinh on 7/30/2015.
 */
public class GameScreen implements Screen {
    final HappyBird game;
    OrthographicCamera camera;
    OrthographicCamera uiCamera;
    GameState state;
    float groundOffsetX = 0;
    private static final float BIRD_JUMP_IMPULSE = 350;
    private static final float GRAVITY = -20;
    private static final float BIRD_VELOCITY_X = 200;
    private static final float BIRD_START_Y = 240;
    private static final float BIRD_START_X = 50;
    int score, highscore;
    boolean gameIsStart, isSaveHighscore, isShowAds;
    Vector2 birdPosition = new Vector2();
    Vector2 birdVelocity = new Vector2();
    float birdStateTime = 0;
    Vector2 gravity = new Vector2();
    Array<Rock> rocks = new Array<Rock>();

    Rectangle rect1 = new Rectangle();
    Rectangle rect2 = new Rectangle();

    Rectangle rectQuit;
    Rectangle rectRestart;
    Rectangle rectRateGame;
    Rectangle rectWorldScore;

    public GameScreen(HappyBird game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.update();
        rectRestart=new Rectangle(340,150,140,60);
        rectQuit=new Rectangle(120,30,140,60);
        rectRateGame=new Rectangle(350,30,140,60);
        rectWorldScore=new Rectangle(570,30,110,80);
        resetWorld();
    }
    private void resetWorld() {
        score = 0;
        state = GameState.Ready;
        gameIsStart=false;
        isSaveHighscore=false;
        isShowAds=false;
        groundOffsetX = 0;
        birdPosition.set(BIRD_START_X, BIRD_START_Y);
        birdVelocity.set(0, 0);
        gravity.set(0, GRAVITY);
        camera.position.x = 400;
        game.actionResolver.hideAds();
        rocks.clear();
        for(int i = 0; i < 5; i++) {
            boolean isDown = MathUtils.randomBoolean();
            rocks.add(new Rock(700 + i * 200, isDown?480-game.rockBottom.getRegionHeight(): 0, isDown? game.rockTop: game.rockBottom));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.background, camera.position.x - game.background.getWidth() / 2, 0);
        for(Rock rock: rocks) {
            game.batch.draw(rock.image, rock.position.x, rock.position.y);
        }
        game.batch.draw(game.floor, groundOffsetX, 0);
        game.batch.draw(game.floor, groundOffsetX + game.floor.getRegionWidth(), 0);
        game.batch.draw(game.ceiling, groundOffsetX, 480 - game.ceiling.getRegionHeight());
        game.batch.draw(game.ceiling, groundOffsetX + game.ceiling.getRegionWidth(), 480 - game.ceiling.getRegionHeight());
        game.batch.draw(game.bird,birdPosition.x,birdPosition.y);
        game.batch.end();

        game.batch.setProjectionMatrix(uiCamera.combined);
        game.batch.begin();
        GlyphLayout layout = new GlyphLayout(game.font, String.valueOf(score));
        game.font.draw(game.batch, layout, camera.viewportWidth / 2 - layout.width / 2, 480 - 100);
        game.batch.end();
    }


    private void restartGame() {
        resetWorld();
    }

    private void updateReady() {
        if (Gdx.input.justTouched()) {
            state = GameState.Running;
        }
        game.batch.begin();
        game.batch.draw(game.ready, camera.viewportWidth / 2 - game.ready.getWidth() / 2, camera.viewportHeight / 2 - game.ready.getHeight() / 2);
        game.batch.end();

    }

    public void updateRunning() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            birdVelocity.set(BIRD_VELOCITY_X,BIRD_JUMP_IMPULSE);
        }

        birdVelocity.add(gravity);
        birdPosition.mulAdd(birdVelocity, deltaTime);
        camera.position.x = birdPosition.x + 350;
        if(camera.position.x - groundOffsetX > game.floor.getRegionWidth() + 400) {
            groundOffsetX += game.floor.getRegionWidth();
        }
        rect1.set(birdPosition.x + 20, birdPosition.y, game.bird.getWidth() - 20, game.bird.getHeight());
        for(Rock r: rocks) {
            if(camera.position.x - r.position.x > 400 + r.image.getRegionWidth()) {
                boolean isDown = MathUtils.randomBoolean();
                r.position.x += 5 * 200;
                r.position.y = isDown?480-game.rockTop.getRegionHeight(): 0;
                r.image = isDown? game.rockTop:game.rockBottom;
                r.counted = false;
            }
            rect2.set(r.position.x + (r.image.getRegionWidth() - 30) / 2 + 20, r.position.y, 20, r.image.getRegionHeight() - 10);
            if(rect1.overlaps(rect2)) {
                state = GameState.GameOver;
            }
            if(r.position.x < birdPosition.x && !r.counted) {
                score++;
                r.counted = true;
            }
        }
        if (birdPosition.y<game.floor.getRegionHeight()-20||birdPosition.y>480-game.ceiling.getRegionHeight()+game.bird.getHeight()-20)
        {
            state = GameState.GameOver;
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
            uiCamera.unproject(v);

            if (Helpers.isTouchedInRect(rectRestart, v.x, v.y)) {
                restartGame();
            }
            if (Helpers.isTouchedInRect(rectRateGame, v.x, v.y)) {
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.samsam.newblock");
            }
            if (Helpers.isTouchedInRect(rectQuit, v.x, v.y)) {
                Gdx.app.exit();
            }
            if (Helpers.isTouchedInRect(rectWorldScore, v.x, v.y)) {
                restartGame();
            }
        }
        if (!isShowAds) {
            game.actionResolver.showAds();
            isShowAds=true;
        }

        game.batch.begin();
        game.batch.draw(game.gameover,0,0);
        //GlyphLayout layout = new GlyphLayout(game.font, String.valueOf(highscore));



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

    static class Rock {
        Vector2 position = new Vector2();
        TextureRegion image;
        boolean counted;

        public Rock(float x, float y, TextureRegion image) {
            this.position.x = x;
            this.position.y = y;
            this.image = image;
        }
    }
}
