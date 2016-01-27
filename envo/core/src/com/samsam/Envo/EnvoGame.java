package com.samsam.Envo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
	Texture backtop;
	Texture english;
	Texture vietnamese;
	String[] categoryNames;
	String[] downloadUrls;
	boolean isMute;
	boolean isEnglish;
	String d;
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

		backtop = new Texture("topback.png");
		english = new Texture("english.png");
		vietnamese = new Texture("vietnamese.png");

		d=Helpers.getDownloadHostName();
		categoryNames = new String[] {"Animal","Fruit","Vegetable","Food & Drink","House","Bed room","Kitchen","Living room","Bathroom","Workshop","Flower","Vehicle","Transportation"
		,"School","Fashion","Library","Military","Vehicle","Transportation","Flower","School","Fashion","Travel","Space","Science lab","School subjects","Math","Energy","Farming","Construction","Office",
		"Airport","Occupations","Beach","Park","Sports", "Musical Instruments","Theater","Electronics","Cafe","Restaurant","Weather","Hotel","Make up","Family","Human body"};

		downloadUrls = new String[] {"1",d+"s/udkb01zfc0giv71/2.a?dl=1",d+"s/qr8ijwl8o8w1ida/3.a?dl=1",d+"s/a9ap8t2eec0pncq/4.a?dl=1",d+"s/arfn9t6y6cxv6ql/5.a?dl=1",d+"s/z2uihyq0xdk6xsn/6.a?dl=1",d+"s/lklgxreqigoun4m/7.a?dl=1",d+"s/28poug2wecp6bjo/8.a?dl=1",
										d+"s/uaonupxa8ttafog/9.a?dl=1",d+"s/vpwlviq7dcpquhi/10.a?dl=1",d+"s/oyujt7f839i232j/11.a?dl=1",d+"s/67dquuqa40zl89w/12.a?dl=1",d+"s/ds0jskihcfihfee/13.a?dl=1",d+"s/k5nodn0fabb8yrx/14.a?dl=1",d+"s/9x8dx0y22tsc9kl/15.a?dl=1","16","17","18"};

		FreeTypeFontGenerator generator6 = new FreeTypeFontGenerator(Gdx.files.internal("font/chuviet1.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter6 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter6.size = 30;
		parameter6.characters="aẤểlơÚữáẦÊLớÙỮàẬẾmờỤõạẨỀMợỦÕảbỆnởưỗABỂNƠứỖÁcfoỚừỡÀCFóỜựỠẠdgòỢửĩẢDGọỞƯĨăđhỏpỨỵắĐH​OPỪýằeiÓqỰỳặéíÒQỬỷẳèìỌrvãỹĂẹịỎRVÃỴẮẻỉôswẵÝẰEIốSWẴỲẶÉÍồtxẫỶẲÈÌộTXẪỸâẸỊổuyẽấẺỈÔúYẼ​ầêjỐùzễậếJỒụZỄẩềkỘủũÂệKỔUŨ0123456789&*][_!$%#@|\\/?-+=:'\".,";
		font = generator6.generateFont(parameter6);
		font.setColor(255, 255, 255, 1);
		generator6.dispose();
		isMute=false;
		isEnglish=true;

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
				Gdx.files.internal("1/names-en.data").copyTo(new FileHandle(dirCategory1));
				Gdx.files.internal("1/names-vi.data").copyTo(new FileHandle(dirCategory1));
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
		backtop.dispose();
		english.dispose();
		vietnamese.dispose();
	}
}
