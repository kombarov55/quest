package com.ovt.quest.horce_racing

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ovt.quest.QuestGame
import com.ovt.quest.horce_racing.layout.FinishTable
import com.ovt.quest.quest.commons.Textures


class HorceRacingScreen(game: QuestGame): ScreenAdapter() {

    val stage = Stage()

    val finishTable = FinishTable(game)
    val bgt = Textures.getTexture("фон-конюшни-1.png")
    val bg = Image(bgt).apply {
        x = 0f
        y = 0f
        width = Gdx.graphics.width.toFloat()
        height = Gdx.graphics.height.toFloat()
    }

    override fun show() {
        Gdx.input.inputProcessor = stage

        stage.addActor(bg)
        stage.addActor(finishTable)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.draw()
    }

    override fun dispose() {
        bgt.dispose()
    }
}