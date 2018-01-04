package com.ovt.quest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ovt.quest.QuestGame;

/**
 * Created by kombarov_na on 26.12.2017.
 */

public class QuestScreen implements Screen {

    QuestGame game;

    Stage stage = new Stage();

    private Array<TextButton> optionButtons = new Array<>();
    private Table optionsTable;


    public QuestScreen(QuestGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        stage.addActor(game.background);

        Label title = new Label("Заголовок", game.titleLabelStyle);

        Label description = new Label("Здесь будет очень длинное описание, которое игрок должен будет внимательно прочитать и сделать выбор на основе предложенных вариантов", game.descriptionStyle);
        description.setWrap(true);
        description.setAlignment(Align.center);

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        table.add(title).padTop(Gdx.graphics.getHeight() * 0.05f);
        table.row();
        table.add(description).width(Gdx.graphics.getWidth() * 0.9f).padBottom(Gdx.graphics.getHeight() * 0.15f);
        table.row();

        optionsTable = new Table();
        game.switchFontBug(game.smallerFont);

        for (int i = 0; i < 4; i++) {
            TextButton optionButton = new TextButton("Развилка " + (i + 1), game.textButtonSkin);
            optionButton.getStyle().font = game.smallerFont;
            optionButton.getStyle().font = game.smallerFont;

            optionButtons.add(optionButton);

            if (i % 2 == 0 && i != 0) {
                optionsTable.row();
            }

            optionsTable.add(optionButton).width(Gdx.graphics.getWidth() * 0.45f).height(Gdx.graphics.getHeight() * 0.1f).pad(Gdx.graphics.getWidth() * 0.005f);
        }

        table.add(optionsTable).expandY().bottom().padBottom(Gdx.graphics.getHeight() * 0.1f);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
}
