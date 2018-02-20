package com.ovt.quest.main_menu_screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.commons.QuestGame

/**
 * Created by nikolay on 05/01/2018.
 */

class MinigamesScreen(private val game: QuestGame) : Screen {

    private val stage = Stage()

    override fun show() {
        stage.addActor(game.background)

        val table = Table()
        table.debug = true
        table.setFillParent(true)


        val archery = game.buttons.smallerButton("Стрельба из лука")
        table.add(archery)
        table.row()

        stage.addActor(table)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.draw()
    }

    override fun resize(width: Int, height: Int) {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {

    }
}
