package com.ovt.quest.main_menu_screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.ovt.quest.QuestGame
import com.ovt.quest.archery.ArcheryScreen
import com.ovt.quest.commons.addClickListener
import com.ovt.quest.main_menu_screens.layout.MinigamesStage
import com.ovt.quest.three_in_a_row.ThreeInARowScreen

/**
 * Created by nikolay on 14.03.18.
 */
class MinigamesScreen(private val game: QuestGame) : Screen {

    private val stage = MinigamesStage(game)


    override fun show() {
        Gdx.input.inputProcessor = stage
        stage.threeInARowGameButton.addClickListener { game.screen = ThreeInARowScreen(game) }
        stage.archeryButton.addClickListener { game.screen = ArcheryScreen(game) }
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        stage.draw()
    }

    override fun hide() {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

    }
}