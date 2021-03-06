package com.ovt.quest.horce_racing

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ovt.quest.QuestGame
import com.ovt.quest.horce_racing.layout.FinishTable


class HorceRacingScreen(game: QuestGame): ScreenAdapter() {

    val stage = Stage()

    val finishTable = FinishTable(game)

    override fun show() {
        Gdx.input.inputProcessor = stage

        stage.addActor(finishTable)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.draw()
    }
}