package samsam.colortest.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

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
    Rectangle fbRect;
    Rectangle restartRect;

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
        bgRestart = new Texture("play-again-3.png");
        fbRect=new Rectangle();
        restartRect=new Rectangle();
        //game.actionResolver.showSmallGoogleAds();
    }

    private ResultInfo getResultInfo()
    {
        if (1 <= score && score < 4) {
            return  new ResultInfo("1.png",languagesManager.getString("bat"),languagesManager.getString("bat1"),languagesManager.getString("bat2"),"http://4.bp.blogspot.com/-8sgrz0ydczw/Va-kTG4TcaI/AAAAAAAAPQg/Iqp6C-brXSw/s1600/1.png");
        }
        if (4 <= score && score < 8) {
            return  new ResultInfo("2.png",languagesManager.getString("mole"),languagesManager.getString("mole1"),languagesManager.getString("mole2"),"http://3.bp.blogspot.com/-3wEHf1hE8MA/Va-kTOJzsOI/AAAAAAAAPQs/y6GX7zZRiYM/s1600/2.png");
        }
        if (8 <= score && score < 11) {
            return  new ResultInfo("3.png",languagesManager.getString("dog"),languagesManager.getString("dog1"),languagesManager.getString("dog2"),"http://1.bp.blogspot.com/-nxQeg10aOEg/Va-kTGFeBQI/AAAAAAAAPQ0/Lgke2fXpNUo/s1600/3.png");
        }
        if (11 <= score && score < 15) {
            return  new ResultInfo("4.png",languagesManager.getString("cat"),languagesManager.getString("cat1"),languagesManager.getString("cat2"),"http://4.bp.blogspot.com/-YOwRPTaPysU/Va-kUIZtvhI/AAAAAAAAPRE/ZoXHtV5T2LU/s1600/4.png");
        }
        if (15 <= score && score < 20) {
            return  new ResultInfo("5.png",languagesManager.getString("tiger"),languagesManager.getString("tiger1"),languagesManager.getString("tiger2"),"http://3.bp.blogspot.com/-pxmC2IRsBpg/Va-kUfqEFFI/AAAAAAAAPQ4/tTzQV39YPKk/s1600/5.png");
        }
        if (20 <= score && score < 30) {
            return  new ResultInfo("6.png",languagesManager.getString("hawk"),languagesManager.getString("hawk1"),languagesManager.getString("hawk2"),"http://1.bp.blogspot.com/-_ow30ljT6tE/Va-kUtIPAvI/AAAAAAAAPRA/tiogmPNUNCE/s1600/6.png");
        }

        return  new ResultInfo("7.png",languagesManager.getString("alien"),languagesManager.getString("alien1"),languagesManager.getString("alien2"),"http://2.bp.blogspot.com/-cN7JfcBef9o/Va-kU2iZjNI/AAAAAAAAPRI/sURgPojcFWc/s1600/7.png");
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

        fbRect.set(camera.viewportWidth / 2 - bgFacebook.getWidth() / 2, 330, bgFacebook.getWidth(), bgFacebook.getHeight());
        restartRect.set(camera.viewportWidth/2-bgRestart.getWidth()/2,240,bgRestart.getWidth(),bgRestart.getHeight());
        game.batch.draw(bgFacebook,fbRect.x,fbRect.y);
        game.batch.draw(bgRestart,restartRect.x,restartRect.y);
        game.fontResultText.draw(game.batch,languagesManager.getString("shareFacebook"),camera.viewportWidth/2-bgFacebook.getWidth()/2+33,374);
        game.batch.end();

        if (Gdx.input.justTouched())
        {
            Vector3 v = new Vector3();
            v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            if (Helpers.isTouchedInRect(fbRect,v.x,v.y))
            {
                //fb tap
                game.actionResolver.showFbShare(
                        getResultInfo().get_name(),
                        languagesManager.getString("yourScore")+" "+String.valueOf(score) +". "+getResultInfo().get_description1()+". "+getResultInfo().get_description2()+". " + languagesManager.getString("trythis"),
                        "http://funnynet.net",getResultInfo().get_picture_fb()
                );
            }
            if (Helpers.isTouchedInRect(restartRect,v.x,v.y))
            {
                //restart tap
                game.setScreen(new GameScreen(game));
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
