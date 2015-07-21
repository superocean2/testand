package samsam.colortest.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ColorTest extends Game {
	SpriteBatch batch;
	BitmapFont font;
	BitmapFont fontStart;
	BitmapFont fontScore;
	BitmapFont fontTimeleft;
	BitmapFont fontResultScore;
	BitmapFont fontResultText;
	BitmapFont fontResultTitle;
	Texture background;
	Texture button;
	ActionResolver actionResolver;

	public ColorTest(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}

	@Override
	public void create () {
//		FileHandle baseFileHandle = Gdx.files.internal("Languages/Bundle");
//		languageBundle = I18NBundle.createBundle(baseFileHandle);

		batch = new SpriteBatch();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/result_score.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 20;
		parameter.characters="aẤểlơÚữáẦÊLớÙỮàẬẾmờỤõạẨỀMợỦÕảbỆnởưỗABỂNƠứỖÁcfoỚừỡÀCFóỜựỠẠdgòỢửĩẢDGọỞƯĨăđhỏpỨỵắĐH​OPỪýằeiÓqỰỳặéíÒQỬỷẳèìỌrvãỹĂẹịỎRVÃỴẮẻỉôswẵÝẰEIốSWẴỲẶÉÍồtxẫỶẲÈÌộTXẪỸâẸỊổuyẽấẺỈÔúYẼ​ầêjỐùzễậếJỒụZỄẩềkỘủũÂệKỔUŨ0123456789][_!$%#@|\\/?-+=:'\".,";
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
		font.setColor(1, 0, 0, 1);

		FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("font/result_score.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter1.size = 30;
		parameter1.characters="aẤểlơÚữáẦÊLớÙỮàẬẾmờỤõạẨỀMợỦÕảbỆnởưỗABỂNƠứỖÁcfoỚừỡÀCFóỜựỠẠdgòỢửĩẢDGọỞƯĨăđhỏpỨỵắĐH​OPỪýằeiÓqỰỳặéíÒQỬỷẳèìỌrvãỹĂẹịỎRVÃỴẮẻỉôswẵÝẰEIốSWẴỲẶÉÍồtxẫỶẲÈÌộTXẪỸâẸỊổuyẽấẺỈÔúYẼ​ầêjỐùzễậếJỒụZỄẩềkỘủũÂệKỔUŨ0123456789][_!$%#@|\\/?-+=:'\".,";
		fontStart = generator1.generateFont(parameter1); // font size 12 pixels
		fontStart.setColor(255, 255, 255, 1);
		generator1.dispose();

		FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("font/classic.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter2.size = 45;
		fontScore = generator2.generateFont(parameter2); // font size 12 pixels
		fontScore.setColor(255, 255, 255, 1);
		generator2.dispose();

		FreeTypeFontGenerator generator3 = new FreeTypeFontGenerator(Gdx.files.internal("font/classic.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter3.size = 45;
		fontTimeleft = generator3.generateFont(parameter3); // font size 12 pixels
		fontTimeleft.setColor(255, 255, 255, 1);
		generator3.dispose();

		FreeTypeFontGenerator generator4 = new FreeTypeFontGenerator(Gdx.files.internal("font/chuviet1.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter4.size = 28;
		parameter4.characters="aẤểlơÚữáẦÊLớÙỮàẬẾmờỤõạẨỀMợỦÕảbỆnởưỗABỂNƠứỖÁcfoỚừỡÀCFóỜựỠẠdgòỢửĩẢDGọỞƯĨăđhỏpỨỵắĐH​OPỪýằeiÓqỰỳặéíÒQỬỷẳèìỌrvãỹĂẹịỎRVÃỴẮẻỉôswẵÝẰEIốSWẴỲẶÉÍồtxẫỶẲÈÌộTXẪỸâẸỊổuyẽấẺỈÔúYẼ​ầêjỐùzễậếJỒụZỄẩềkỘủũÂệKỔUŨ0123456789][_!$%#@|\\/?-+=:'\".,";
		fontResultText = generator4.generateFont(parameter4); // font size 12 pixels
		fontResultText.setColor(255, 255, 255, 1);
		generator4.dispose();

		FreeTypeFontGenerator generator5 = new FreeTypeFontGenerator(Gdx.files.internal("font/resultscore.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter5 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter5.size = 25;
		parameter5.characters="aẤểlơÚữáẦÊLớÙỮàẬẾmờỤõạẨỀMợỦÕảbỆnởưỗABỂNƠứỖÁcfoỚừỡÀCFóỜựỠẠdgòỢửĩẢDGọỞƯĨăđhỏpỨỵắĐH​OPỪýằeiÓqỰỳặéíÒQỬỷẳèìỌrvãỹĂẹịỎRVÃỴẮẻỉôswẵÝẰEIốSWẴỲẶÉÍồtxẫỶẲÈÌộTXẪỸâẸỊổuyẽấẺỈÔúYẼ​ầêjỐùzễậếJỒụZỄẩềkỘủũÂệKỔUŨ0123456789][_!$%#@|\\/?-+=:'\".,";
		fontResultScore = generator5.generateFont(parameter5); // font size 12 pixels
		fontResultScore.setColor(255, 255, 255, 1);
		generator5.dispose();

		FreeTypeFontGenerator generator6 = new FreeTypeFontGenerator(Gdx.files.internal("font/chuviet1.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter6 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter6.size = 35;
		parameter6.characters="aẤểlơÚữáẦÊLớÙỮàẬẾmờỤõạẨỀMợỦÕảbỆnởưỗABỂNƠứỖÁcfoỚừỡÀCFóỜựỠẠdgòỢửĩẢDGọỞƯĨăđhỏpỨỵắĐH​OPỪýằeiÓqỰỳặéíÒQỬỷẳèìỌrvãỹĂẹịỎRVÃỴẮẻỉôswẵÝẰEIốSWẴỲẶÉÍồtxẫỶẲÈÌộTXẪỸâẸỊổuyẽấẺỈÔúYẼ​ầêjỐùzễậếJỒụZỄẩềkỘủũÂệKỔUŨ0123456789][_!$%#@|\\/?-+=:'\".,";
		fontResultTitle = generator6.generateFont(parameter6); // font size 12 pixels
		fontResultTitle.setColor(255, 255, 255, 1);
		generator6.dispose();


		background = new Texture("bg_colortest.png");
		button = new Texture("button.png");
		setScreen(new StartScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		fontStart.dispose();
		fontScore.dispose();
		fontTimeleft.dispose();
		fontResultScore.dispose();
		fontResultText.dispose();
		fontResultTitle.dispose();
		background.dispose();
		button.dispose();
	}
}
