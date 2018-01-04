package com.ovt.quest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.ovt.quest.screens.MainMenuScreen;

public class QuestGame extends Game {

	public SpriteBatch batch;
	public BitmapFont bigFont;
	public BitmapFont smallerFont;

	public LabelStyle titleLabelStyle;
	public LabelStyle descriptionStyle;

	public Skin textButtonSkin;

    public TextButtonStyle biggerTextButtonStyle;
    public TextButtonStyle smallerTextButtonStyle;


	public Image background;

	@Override
	public void create () {
		batch = new SpriteBatch();

		bigFont = createFont(45);
		smallerFont = createFont(24);

		textButtonSkin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));

		titleLabelStyle = new LabelStyle(bigFont, Color.BLACK);
		descriptionStyle = new LabelStyle(smallerFont, Color.BLACK);

		background = new Image(new Texture(Gdx.files.internal("bg.jpg")));

        TextButton b = new TextButton("", textButtonSkin);
        biggerTextButtonStyle = b.getStyle();
        biggerTextButtonStyle.font = bigFont;

        TextButton sm = new TextButton("", textButtonSkin);
        smallerTextButtonStyle = sm.getStyle();
        smallerTextButtonStyle.font = smallerFont;

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		bigFont.dispose();
	}

	private BitmapFont createFont(int size) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/bankir-retro.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.characters = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
		parameter.size = size;
		parameter.color = Color.BLACK;
		BitmapFont bf = generator.generateFont(parameter);
		generator.dispose();

		return bf;
	}

	// Шрифт добавляется только начиная со второй созданной кнопки. Некогда разбираться почему,
	// поэтому я создаю здесь пустую кнопку. Нужно вызывать при переключении шрифтов
	public void switchFontBug(BitmapFont font) {
		TextButton stub = new TextButton("", textButtonSkin);
		stub.getStyle().font = font;
	}
}
