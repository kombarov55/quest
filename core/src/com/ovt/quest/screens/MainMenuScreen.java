package com.ovt.quest.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ovt.quest.QuestGame;

/**
 * Created by kombarov_na on 21.12.2017.
 */

public class MainMenuScreen implements Screen {

    QuestGame game;

    Stage stage = new Stage();

    public MainMenuScreen(QuestGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        stage.addActor(game.background);

        game.switchFontBug(game.bigFont);

        TextButton playButton = new TextButton("Играть", game.biggerTextButtonStyle);
        playButton.addListener(toQuestScreen);

        TextButton exitButton = new TextButton("Выход", game.biggerTextButtonStyle);
        exitButton.addListener(exit);

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().width(Gdx.graphics.getWidth() * 0.50f);

        table.add(playButton);
        table.row();
        table.add(exitButton);

        stage.addActor(table);
    }

    private ClickListener toQuestScreen = new ClickListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            game.setScreen(new QuestScreen(game));
        }
    };

    private ClickListener exit = new ClickListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.exit();
        }
    };

    @Override
    public void render(float delta) {
        game.batch.begin();
        stage.draw();
        game.bigFont.draw(game.batch, "asdjfkl; фываолдж", 150, 150);
        game.batch.end();
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
