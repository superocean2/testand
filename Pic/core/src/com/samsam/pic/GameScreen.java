package com.samsam.pic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;


/**
 * Created by NghiaTrinh on 8/11/2015.
 */
public class GameScreen implements Screen {

    final PicGame game;
    OrthographicCamera camera;
    Texture picture;
    Music humanEn;
    Music humanVi;
    Music animal;
    Rectangle rectLeft;
    Rectangle rectRight;
    Rectangle rectHuman;
    Rectangle rectAnimal;
    Rectangle rectLanguage;
    boolean isMuteHuman;
    boolean isMuteAnimal;
    boolean isEnglish;
    boolean isHided;
    boolean isCompleteDelay;
    boolean isTouched;
    boolean isSetdelayed;
    boolean isHaveAnimalSound;
    int screenId;
    int[] arr={1,5,7,10,13,15,19,28,36,40,44,45,46,51,52,54,61,62,67};

    public GameScreen(PicGame game, GameScreenInfo info) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 800);

        isHaveAnimalSound=false;
        screenId=info.getScreenId();
        picture = new Texture(screenId+".png");
        humanEn = Gdx.audio.newMusic(Gdx.files.internal("sound/english/"+screenId+".mp3"));
        humanVi = Gdx.audio.newMusic(Gdx.files.internal("sound/english/"+screenId+".mp3"));
        if (!info.getAnimal().isEmpty()) {
            isHaveAnimalSound=true;
            animal = Gdx.audio.newMusic(Gdx.files.internal("sound/animal/"+screenId+".mp3"));
        }


        isMuteHuman=false;
        isMuteAnimal=false;
        isEnglish=true;
        isHided=false;
        isCompleteDelay=false;
        isTouched=false;
        isSetdelayed=false;

        if (isHaveAnimalSound) {
            rectAnimal = new Rectangle(camera.viewportWidth / 2 - game.animal.getWidth() / 2, camera.viewportHeight - game.animal.getHeight() - 20, game.animal.getWidth(), game.animal.getHeight());
            rectLanguage = new Rectangle(rectAnimal.x + game.animal.getWidth() + 20, rectAnimal.y, game.vietnamese.getWidth(), game.vietnamese.getHeight());
            rectHuman = new Rectangle(rectAnimal.x - game.human.getWidth() - 20, rectAnimal.y, game.human.getWidth(), game.human.getHeight());
        }
        else
        {
            rectHuman = new Rectangle(540,camera.viewportHeight-game.human.getHeight()-20,game.human.getWidth(),game.human.getHeight());
            rectLanguage = new Rectangle(rectHuman.x+20,rectHuman.y,game.vietnamese.getWidth(),game.vietnamese.getHeight());
        }
        rectLeft = new Rectangle(0,0,game.left.getWidth(),game.left.getHeight());
        rectRight = new Rectangle(camera.viewportWidth-game.right.getWidth(),0,game.right.getWidth(),game.right.getHeight());

        if (!isMuteHuman) {
            if (isEnglish)
            {
                humanEn.play();
            }
            else
            {
                humanVi.play();
            }
            humanEn.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    if (!isMuteAnimal&&isHaveAnimalSound)
                    {
                        animal.play();
                    }
                }
            });
            humanVi.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    if (!isMuteAnimal&&isHaveAnimalSound)
                    {
                        animal.play();
                    }
                }
            });
        }
        else {
            if (!isMuteAnimal && isHaveAnimalSound) {
                animal.play();
            }
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(picture, 0, 0);
        game.batch.end();

        if (Gdx.input.isTouched())
        {
            if (!isSetdelayed)
            {
                isTouched=true;
                setDelay();
                isSetdelayed=true;
                isCompleteDelay=false;
            }
            else
            {
                if (isCompleteDelay)
                {
                    isSetdelayed=false;
                }
            }
            drawOption();
        }
        else {
            if (isTouched && !isCompleteDelay) {
                drawOption();
            }
        }

        if (isHided)
        {
            this.dispose();
        }
    }

    private void setDelay()
    {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                isCompleteDelay=true;
                isTouched = false;
            }
        },5);
    }
    private void drawOption()
    {
        game.batch.begin();
        game.batch.draw(isMuteHuman ? game.muteHuman : game.human, rectHuman.x, rectHuman.y);
        if (isHaveAnimalSound) {
            game.batch.draw(isMuteAnimal ? game.muteAnimal : game.animal, rectAnimal.x, rectAnimal.y);
        }
        game.batch.draw(isEnglish ? game.vietnamese : game.english, rectLanguage.x, rectLanguage.y);
        if (screenId>1) {
            game.batch.draw(game.left, rectLeft.x, rectLeft.y);
        }
        if (screenId<70) {
            game.batch.draw(game.right, rectRight.x, rectRight.y);
        }
        game.batch.end();

        if (Gdx.input.justTouched())
        {
            Vector3 v=new Vector3();
            v.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(v);



            if (Helpers.isTouchedInRect(rectLeft,v.x,v.y))
            {

            }
            if (Helpers.isTouchedInRect(rectRight,v.x,v.y))
            {
                int nextScreen=screenId+1;
                game.setScreen(new GameScreen(game,new GameScreenInfo(""+nextScreen+".png",""+nextScreen+".mp3",""+nextScreen+".mp3",""+nextScreen+".mp3",nextScreen)));
            }
            if (Helpers.isTouchedInRect(rectHuman,v.x,v.y))
            {
                if (isMuteHuman) isMuteHuman=false;
                else isMuteHuman=true;
            }
            if (isHaveAnimalSound) {
                if (Helpers.isTouchedInRect(rectAnimal, v.x, v.y)) {
                    if (isMuteAnimal) isMuteAnimal=false;
                    else isMuteAnimal=true;
                }
            }
            if (Helpers.isTouchedInRect(rectLanguage,v.x,v.y))
            {
                if (isEnglish) isEnglish=false;
                else isEnglish=true;
            }
        }
    }

    @Override
    public void hide() {
        isHided=true;
    }

    @Override
    public void dispose() {
        picture.dispose();
        humanEn.dispose();
        humanVi.dispose();
        if (isHaveAnimalSound) {
            animal.dispose();
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

}
