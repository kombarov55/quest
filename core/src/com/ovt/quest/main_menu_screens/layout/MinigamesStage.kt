package com.ovt.quest.main_menu_screens.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 14.03.18.
 */
class MinigamesStage(private val game: QuestGame) : Stage() {


    val threeInARowGameButton = game.buttons.biggerButton("3 в ряд")

    val archeryButton = game.buttons.biggerButton("Стрельба")


    init {
        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        val table = Table()
        table.setFillParent(true)
        table.top().padTop(h * 0.1f)
        table.defaults().width(w * 0.8f)
        table.add(threeInARowGameButton)
        table.row()
        table.add(archeryButton)
        table.row()

        addActor(table)
    }

}