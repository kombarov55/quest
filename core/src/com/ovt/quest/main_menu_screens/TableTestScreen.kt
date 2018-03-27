package com.ovt.quest.main_menu_screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 18/02/2018.
 */
class TableTestScreen(private val game: QuestGame) : Screen {

    private val stage = Stage()
    private val table = Table()

    private val homeButton = game.buttons.normalButton("Главное меню", onClick = {
        stage.actors.removeValue(table, false)
    })
    private val settingsButton = game.buttons.normalButton("Настройки")


    override fun show() {
        Gdx.input.inputProcessor = stage


        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        table.width = w * 0.8f
        table.height = h * 0.8f

        table.setBackground(Image(Texture(Gdx.files.internal("img/settings-table-bg.jpg"))).drawable)
        table.setPosition(w * 0.1f, h * 0.1f)
        table.top().padTop(h * 0.1f)
        table.defaults().expandX().pad(h * 0.025f).width(table.width * 0.8f)

        table.add(settingsButton).top()
        table.row()
        table.add(homeButton)

        stage.addActor(game.background)
        stage.addActor(table)
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