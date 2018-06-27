package com.ovt.quest.horce_racing

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.ovt.quest.QuestGame
import com.ovt.quest.horce_racing.layout.FinishTable


class HorceRacingScreen(game: QuestGame): ScreenAdapter() {

    val sb = SpriteBatch()
    val finishTable = FinishTable()

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        sb.begin()
        finishTable.draw(sb, 1f)
        sb.end()
    }
}