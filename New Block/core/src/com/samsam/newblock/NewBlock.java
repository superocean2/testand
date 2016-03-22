package com.samsam.newblock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;

public class NewBlock extends Game {
    SpriteBatch batch;
    Texture background;
    Texture block;
    Texture disableMedal;
    Texture enableMedal;
    Texture ready;
    BitmapFont font;
    ActionResolver actionResolver;
    int blockWidth;
    int blockHeight;
    Rectangle rectScreen;


    public NewBlock(ActionResolver actionResolver) {
        this.actionResolver = actionResolver;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        block = new Texture("block.png");
        disableMedal = new Texture("disablemedal.png");
        enableMedal = new Texture("enablemedal.png");
        ready = new Texture("ready.png");
        blockWidth=30;
        blockHeight=30;
        rectScreen = new Rectangle(0,0,390,630);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.characters="0123456789";
        font = generator.generateFont(parameter);
        font.setColor(255, 255, 255, 1);
        generator.dispose();

        setScreen(new GameScreen(this));
    }
    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        block.dispose();
        disableMedal.dispose();
        enableMedal.dispose();
        ready.dispose();
    }
}
