package com.samsam.newblock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Timer;


/**
 * Created by NghiaTrinh on 7/30/2015.
 */
public class GameScreen implements Screen {
    final NewBlock game;
    OrthographicCamera camera;
    GameState state;
    World world;
    int score, highscore, startSpeed, currentSpeed;
    boolean gameIsStart, isSaveHighscore, isIronSpeed, isSilverSpeed, isGoldSpeed;
    Rectangle rectLeft;
    Rectangle rectRight;
    Rectangle rectClose;
    Rectangle rectPause;
    Rectangle rectIron;
    Rectangle rectSilver;
    Rectangle rectGold;
    Rectangle rectRestart;
    Rectangle rectRateGame;

    public GameScreen(NewBlock game) {
        this.game = game;
        if (Helpers.getStatus() == 1)
            state = GameState.Ready;
        else
            state = GameState.Instruction;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 410, 725);
        world = new World();
        score = 0;
        gameIsStart = false;
        isIronSpeed = isSilverSpeed = isGoldSpeed = false;
        rectLeft = new Rectangle(0, 0, game.rectScreen.width / 2, game.rectScreen.height);
        rectRight = new Rectangle(game.rectScreen.width / 2, 0, game.rectScreen.width / 2, game.rectScreen.height);
        rectPause = new Rectangle(0, 560, 100, 100);
        highscore = Helpers.getHighScore();

        float x, y;
        x = camera.viewportWidth / 2 - game.gameover.getWidth() / 2;
        y = camera.viewportHeight / 2 - game.gameover.getHeight() / 2;

        isSaveHighscore = false;
        rectIron = new Rectangle(x + 30, y + 105, 87, 87);
        rectSilver = new Rectangle(x + 30 + 114, y + 100, 87, 87);
        rectGold = new Rectangle(x + 30 + 225, y + 100, 87, 87);
        rectRestart = new Rectangle(x + 190, y + 35, 150, 40);
        rectRateGame = new Rectangle(x + 30, y + 35, 110, 40);

        startSpeed = Speed.EASY;
        currentSpeed = startSpeed;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        drawWorld();

        if (state == GameState.Instruction)
            updateInstruction();
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
        game.batch.draw(game.pause, 45, 595);
        if (score < world.score) {
            game.getScoreSoud.play(0.1f);
        }
        score = world.score;

        if (score >= Medal.IRON && score < Medal.SILVER) {
            game.batch.draw(new TextureRegion(game.miniMedal, 0, 0, 30, 30), 78, 593);
            if (!isIronSpeed) {
                currentSpeed = Speed.IRON;
                world.increaseSpeed(currentSpeed);
                isIronSpeed = true;
            }
        }
        if (score >= Medal.SILVER && score < Medal.GOLD) {
            game.batch.draw(new TextureRegion(game.miniMedal, 30, 0, 30, 30), 78, 593);
            if (!isSilverSpeed) {
                currentSpeed = Speed.SILVER;
                world.increaseSpeed(currentSpeed);
                isSilverSpeed = true;
            }
        }
        if (score >= Medal.GOLD) {
            game.batch.draw(new TextureRegion(game.miniMedal, 60, 0, 30, 30), 78, 593);
            if (!isGoldSpeed) {
                currentSpeed = Speed.GOLD;
                world.increaseSpeed(currentSpeed);
                isGoldSpeed = true;
            }
        }

        game.font.setColor(1, 1, 1, 1);
        GlyphLayout layout = new GlyphLayout(game.font, String.valueOf(score));
        game.font.draw(game.batch, layout, (int) (0.75 * camera.viewportWidth) + 30 - layout.width / 2, 620);

        //draw wait block
        for (int j = 0; j < world.main_figure.data.length; j++) {
            int x = (int) game.rectScreen.x + (world.main_figure.x + world.main_figure.data[j][0]) * game.blockWidth;
            int y = (int) game.rectScreen.y + (world.main_figure.y + world.main_figure.data[j][1]) * game.blockHeight;
            game.batch.draw(getTextureColor(world.main_figure.color), x, y);
        }
        //draw current block
        for (int i = 0; i < world.gridcols + 2; i++)
            for (int j = 0; j < world.gridrows + 2; j++) {
                if (world.pool[i][j] != 0) {
                    float x = game.rectScreen.x + (i - 1) * game.blockWidth;
                    float y = game.rectScreen.y + (j - 1) * game.blockHeight;
                    if (game.rectScreen.x < x + 1 && x < game.rectScreen.x + game.rectScreen.width
                            && y >= game.rectScreen.y && y < game.rectScreen.y + game.rectScreen.height) {
                        game.batch.draw(getTextureColor(world.poolColor[i][j]), x, y);
                    }
                }
            }
        game.batch.end();

    }

    private TextureRegion getTextureColor(int color) {
        switch (color) {
            case 0:
                return new TextureRegion(game.block, 0, 0, 30, 30);
            case 1:
                return new TextureRegion(game.block, 30, 0, 30, 30);
            case 2:
                return new TextureRegion(game.block, 60, 0, 30, 30);
            case 3:
                return new TextureRegion(game.block, 90, 0, 30, 30);
            case 4:
                return new TextureRegion(game.block, 0, 30, 30, 30);
            case 5:
                return new TextureRegion(game.block, 30, 30, 30, 30);
            case 6:
                return new TextureRegion(game.block, 60, 30, 30, 30);
            case 7:
                return new TextureRegion(game.block, 90, 30, 30, 30);
            default:
                return new TextureRegion(game.block, 0, 0, 30, 30);
        }
    }

    private void restartGame() {
        world.restart();
        world = new World();
        score = 0;
        gameIsStart = false;
        isIronSpeed = isSilverSpeed = isGoldSpeed = false;
        highscore = Helpers.getHighScore();
        isSaveHighscore = false;
        currentSpeed = Speed.EASY;
        state = GameState.Ready;
    }

    private void updateInstruction() {
        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            float xClose = camera.viewportWidth / 2 - game.instruction.getWidth() / 2 + 110;
            float yClose = camera.viewportHeight / 2 - game.instruction.getHeight() / 2 + 30;
            rectClose = new Rectangle(xClose, yClose, 150, 50);

            if (Helpers.isTouchedInRect(rectClose, v.x, v.y)) {
                Helpers.saveStatus(1);
                state = GameState.Running;
            }

        }

        game.batch.begin();
        game.batch.draw(game.instruction, camera.viewportWidth / 2 - game.instruction.getWidth() / 2, camera.viewportHeight / 2 - game.instruction.getHeight() / 2);
        game.batch.end();
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
            if (Helpers.isTouchedInRect(rectPause, v.x, v.y)) {
                world.pause();
                state = GameState.Paused;
            }
        }
        if (!gameIsStart) {
            world.update(currentSpeed);
            gameIsStart = true;
        }
        if (world.gameOver) {
            game.gameoverSound.play(0.2f);
            state = GameState.GameOver;
        }
    }

    private void updatePaused() {
        if (Gdx.input.justTouched()) {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(game.rectScreen, v.x, v.y)) {
                state = GameState.Running;
                world.resume(currentSpeed);
            }
        }
        game.batch.begin();
        game.batch.draw(game.resume, camera.viewportWidth / 2 - game.resume.getWidth() / 2, camera.viewportHeight / 2 - game.resume.getHeight() / 2);
        game.batch.end();
    }

    private void updateGameOver() {
        if (score > highscore && !isSaveHighscore) {
            Helpers.saveHighScore(score);
            highscore = Helpers.getHighScore();
            isSaveHighscore = true;
        }
        float x, y;
        x = camera.viewportWidth / 2 - game.gameover.getWidth() / 2;
        y = camera.viewportHeight / 2 - game.gameover.getHeight() / 2;

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
        game.batch.draw(game.gameover, x, y);

        GlyphLayout layout = new GlyphLayout(game.font, String.valueOf(highscore));
        game.font.draw(game.batch, layout, camera.viewportWidth/2 - layout.width / 2, y+255);

        if (highscore >= Medal.GOLD) {
            game.batch.draw(new TextureRegion(game.enableMedal, 0, 0, 88, 115), x + 30, y + 105);

            game.batch.draw(new TextureRegion(game.enableMedal, 87, 0, 87, 115), x + 30 + 114, y + 100);

            game.batch.draw(new TextureRegion(game.enableMedal, 174, 0, 87, 115), x + 30 + 225, y + 100);
        }
        if (highscore < Medal.GOLD && highscore >= Medal.SILVER) {

            game.batch.draw(new TextureRegion(game.enableMedal, 0, 0, 88, 115), x + 30, y + 105);

            game.batch.draw(new TextureRegion(game.enableMedal, 87, 0, 87, 115), x + 30 + 114, y + 100);

            game.batch.draw(new TextureRegion(game.disableMedal, 209, 0, 104, 127), x + 30 + 225, y + 100);
        }
        if (highscore < Medal.SILVER && highscore >= Medal.IRON) {

            game.batch.draw(new TextureRegion(game.enableMedal, 0, 0, 88, 115), x + 30, y + 105);

            game.batch.draw(new TextureRegion(game.disableMedal, 105, 0, 98, 127), x + 30 + 110, y + 100);
            game.batch.draw(new TextureRegion(game.disableMedal, 209, 0, 104, 127), x + 30 + 225, y + 100);
        }
        if (highscore < Medal.IRON) {
            game.batch.draw(new TextureRegion(game.disableMedal, 0, 0, 104, 127), x + 30, y + 105);
            game.batch.draw(new TextureRegion(game.disableMedal, 105, 0, 98, 127), x + 30 + 114, y + 100);
            game.batch.draw(new TextureRegion(game.disableMedal, 209, 0, 104, 127), x + 30 + 225, y + 100);
        }

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
        world.pause();
        state = GameState.Paused;
    }

    @Override
    public void resume() {
        world.resume(currentSpeed);
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

    private static class Medal {
        public static int IRON = 5;
        public static int SILVER = 20;
        public static int GOLD = 50;
    }

    private static class Speed {
        public static int EASY = 350;
        public static int IRON = 300;
        public static int SILVER = 250;
        public static int GOLD = 200;
    }
}
