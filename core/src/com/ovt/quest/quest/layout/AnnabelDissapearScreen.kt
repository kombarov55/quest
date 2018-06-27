package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.QuestGame
import com.ovt.quest.quest.QuestScreen


class AnnabelDissapearScreen(private val game: QuestGame): ScreenAdapter() {

    val start = System.currentTimeMillis()

    val stage = Stage()

    val label = game.labelFactory.giantLabel("Аннабель")
    val table = Table()
    init {
        table.setFillParent(true)
        table.add(label)
    }


    override fun show() {
        stage.addActor(table)
        label.addAction(Actions.fadeOut(2f))
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val dt = System.currentTimeMillis() - start
        if (dt < 2000) {
            stage.act()
            stage.draw()
        } else {
            game.screen = QuestScreen(game)
        }
    }
}