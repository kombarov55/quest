package com.ovt.quest.main_menu_screens


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.commons.QuestGame
import com.ovt.quest.quest.QuestScreen

/**
 * Created by kombarov_na on 21.12.2017.
 */
class MainMenuScreen(internal var game: QuestGame) : Screen {

    private val stage = Stage()

    override fun show() {

        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        Gdx.input.inputProcessor = stage

        stage.addActor(game.background)

        val playButton = game.buttons.biggerButton("Играть", { game.screen = QuestScreen(game) })

        val exitButton = game.buttons.biggerButton("Выход", { Gdx.app.exit() })

        val table = Table()
        table.setFillParent(true)
        table.defaults().width(Gdx.graphics.width * 0.8f).pad(h * 0.008f)

        table.add(playButton)
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
