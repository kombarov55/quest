package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : Screen {

    private val stage = ArcheryStage(game)

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
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