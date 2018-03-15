package com.ovt.quest.three_in_a_row

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.ovt.quest.QuestGame
import com.ovt.quest.three_in_a_row.layout.Item
import com.ovt.quest.three_in_a_row.layout.ThreeInARowView

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowScreen(game: QuestGame) : Screen {

    private val view = ThreeInARowView(game)

    override fun show() {
        val item = Item(5, 5, Texture(Gdx.files.internal("img/item_red.png")))

//        view.addItem(item)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.input.inputProcessor = view

        view.act()
        view.draw()
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