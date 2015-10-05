package com.samsam.Envo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.io.File;

public class EnvoGame extends Game {
	SpriteBatch batch;
	Texture bottombg;
	Texture left;
	Texture right;
	BitmapFont font;
	Texture activePage;
	Texture page;
	Texture topbg;
	Texture loudspeaker;
	Texture mutespeaker;
	Texture loadingbg;
	Texture backtop;
	String[] categoryNames;
	String[] downloadUrls;
	boolean isMute;
	ActionResolver actionResolver;

	public EnvoGame(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}
	@Override
	public void create () {
		batch = new SpriteBatch();
		bottombg=new Texture("bottom.png");
		left = new Texture("left.png");
		right=new Texture("right.png");

		topbg = new Texture("top.png");
		page = new Texture("page.png");
		activePage = new Texture("activepage.png");
		loudspeaker = new Texture("loudspeaker.png");
		mutespeaker = new Texture("mutespeaker.png");
		loadingbg = new Texture("downloading.png");
		backtop = new Texture("topback.png");
		categoryNames = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
		downloadUrls = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};

		FreeTypeFontGenerator generator6 = new FreeTypeFontGenerator(Gdx.files.internal("font/chuviet1.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter6 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter6.size = 30;
		//parameter6.characters="123456789";
		font = generator6.generateFont(parameter6);
		font.setColor(255, 255, 255, 1);
		generator6.dispose();
		isMute=false;

		if (!Helpers.getIsLocalDefaultPicture())
		{
			//not saved
			try {
				FileHandle[] imageFiles = Gdx.files.internal("1/pictures/").list();
				FileHandle[] soundFiles = Gdx.files.internal("1/english/").list();
				File dir = new File(Gdx.files.getLocalStoragePath() + "maindata");
				if (!dir.exists()) dir.mkdir();
				File dirCategory1 = new File(dir.getPath() + "/1");
				if (!dirCategory1.exists()) dirCategory1.mkdir();
				File dirPictures = new File(dirCategory1.getPath() + "/pictures");
				if (!dirPictures.exists()) dirPictures.mkdir();
				File dirSounds = new File(dirCategory1.getPath() + "/english");
				if (!dirSounds.exists()) dirSounds.mkdir();

				for (FileHandle file : imageFiles) {
					file.copyTo(new FileHandle(dirPictures));
				}
				for (FileHandle file : soundFiles) {
					file.copyTo(new FileHandle(dirSounds));
				}
				Gdx.files.internal("1/names.data").copyTo(new FileHandle(dirCategory1));
				Helpers.saveIsLocalDefaultPicture(true);
			}
			catch (Exception ex)
			{
				Helpers.saveIsLocalDefaultPicture(false);
			}
		}
		setScreen(new MenuScreen(this, 0));

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose()
	{
		bottombg.dispose();
		left.dispose();
		right.dispose();
		font.dispose();
		activePage.dispose();
		page.dispose();
		topbg.dispose();
		loudspeaker.dispose();
		mutespeaker.dispose();
		loadingbg.dispose();
		backtop.dispose();
	}
}
