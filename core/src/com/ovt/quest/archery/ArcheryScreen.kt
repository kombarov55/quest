package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : Screen {



    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun hide() { }

    override fun dispose() { }

    override fun pause() { }

    override fun resume() { }

    override fun resize(width: Int, height: Int) { }
}