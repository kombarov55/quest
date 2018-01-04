package com.ovt.quest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.ovt.quest.QuestGame;

/**
 * Created by nikolay on 05/01/2018.
 */

public class MinigamesScreen implements Screen {

    private QuestGame game;

    public MinigamesScreen(QuestGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setDebug(true);
        table.setFillParent(true);

    }

    @Override
    public void render(float delta) {

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

    }
}
