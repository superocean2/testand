package samsam.colortest.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import samsam.colortest.com.Model.ResultInfo;

/**
 * Created by NghiaTrinh on 7/6/2015.
 */
public class Result implements Screen{
    final ColorTest game;
    OrthographicCamera camera;
    Texture bgResult;
    Texture resultPicture;
    Texture bgFacebook;
    Texture bgRestart;
    LanguagesManager languagesManager;

    int score;

    public Result(final ColorTest test,int s)
    {
        game = test;
        score=s;
        camera = new OrthographicCamera();
        languagesManager=LanguagesManager.getInstance();
        camera.setToOrtho(false, 480, 800);
        bgResult=new Texture("bg_result.png");
        resultPicture=new Texture(getResultInfo().get_picture());
        bgFacebook= new Texture("facebook.png");
        bgRestart = new Texture("play-again.png");
    }

    private ResultInfo getResultInfo()
    {
        if (1 <= score && score < 4) {
            return  new ResultInfo("1.png",languagesManager.getString("bat"),languagesManager.getString("bat1"),languagesManager.getString("bat2"));
        }
        if (4 <= score && score < 8) {
            return  new ResultInfo("2.png",languagesManager.getString("mole"),languagesManager.getString("mole1"),languagesManager.getString("mole2"));
        }
        if (8 <= score && score < 11) {
            return  new ResultInfo("3.png",languagesManager.getString("dog"),languagesManager.getString("dog1"),languagesManager.getString("dog2"));
        }
        if (11 <= score && score < 15) {
            return  new ResultInfo("4.png",languagesManager.getString("cat"),languagesManager.getString("cat1"),languagesManager.getString("cat2"));
        }
        if (15 <= score && score < 20) {
            return  new ResultInfo("5.png",languagesManager.getString("tiger"),languagesManager.getString("tiger1"),languagesManager.getString("tiger2"));
        }
        if (20 <= score && score < 30) {
            return  new ResultInfo("6.png",languagesManager.getString("hawk"),languagesManager.getString("hawk1"),languagesManager.getString("hawk2"));
        }

        return  new ResultInfo("7.png",languagesManager.getString("alien"),languagesManager.getString("alien1"),languagesManager.getString("alien2"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        game.batch.draw(bgResult, 0, 0);
        game.batch.draw(resultPicture,camera.viewportWidth/2-resultPicture.getWidth()/2,800-280);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(game.fontResultTitle, languagesManager.getString("yourScore") + String.valueOf(score));
        game.fontResultTitle.draw(game.batch, layout, camera.viewportWidth / 2 - layout.width / 2, 800 - 45);
        GlyphLayout layout1 = new GlyphLayout();
        layout1.setText(game.fontResultScore,getResultInfo().get_name());
        game.fontResultScore.draw(game.batch, layout1, camera.viewportWidth / 2 - layout1.width / 2, 800 - 130);

        GlyphLayout layout2 = new GlyphLayout();
        layout2.setText(game.fontResultText, getResultInfo().get_description1());
        game.fontResultText.draw(game.batch, layout2, camera.viewportWidth / 2 - layout2.width / 2, 490);

        GlyphLayout layout3 = new GlyphLayout();
        layout3.setText(game.fontResultText, getResultInfo().get_description2());
        game.fontResultText.draw(game.batch, layout3, camera.viewportWidth / 2 - layout3.width / 2, 460);

        game.batch.draw(bgFacebook,camera.viewportWidth/2-bgFacebook.getWidth()/2,330);
        game.batch.draw(bgRestart,camera.viewportWidth/2-bgRestart.getWidth()/2,270);
        game.batch.end();

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
        bgResult.dispose();
        resultPicture.dispose();
        bgFacebook.dispose();
        bgRestart.dispose();
    }
}
