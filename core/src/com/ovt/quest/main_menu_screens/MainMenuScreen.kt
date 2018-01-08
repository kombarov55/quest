package com.ovt.quest.main_menu_screens


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.ovt.quest.commons.QuestGame

/**
 * Created by kombarov_na on 21.12.2017.
 */

class MainMenuScreen(internal var game: QuestGame) : Screen {

    private val stage = Stage()

    override fun show() {
        Gdx.input.inputProcessor = stage

        stage.addActor(game.background)

        val playButton = game.buttonFactory.biggerButton("Играть", { game.screen = com.ovt.quest.quest.QuestScreen(game) })

        val minigames = game.buttonFactory.biggerButton("Миниигры", { game.screen = MinigamesScreen(game) })

        val exitButton = game.buttonFactory.biggerButton("Выход", { Gdx.app.exit() })

        val table = Table()
        table.setFillParent(true)
        table.defaults().width(Gdx.graphics.width * 0.50f)

        table.add(playButton)
        table.row()
        table.add(minigames)
        table.row()
        table.add(exitButton)

        stage.addActor(table)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.draw()
    }

    override fun resize(width: Int, height: Int) {}

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun dispose() {}

}
