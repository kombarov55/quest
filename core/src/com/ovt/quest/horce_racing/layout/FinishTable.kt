package com.ovt.quest.horce_racing.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.QuestGame
import com.ovt.quest.quest.QuestScreen


class FinishTable(game: QuestGame): Table() {

    val label = game.labelFactory.giantLabel("Вы выйграли!")
    val button = game.buttons.biggerButton("Выйти", onClick = {
        game.screen = (QuestScreen(game))
    })

    init {
        width = Gdx.graphics.width * 0.5f
        height = Gdx.graphics.height * 0.3f
        x = (Gdx.graphics.width / 2) - width / 2
        y = (Gdx.graphics.height / 2) - height / 2

        defaults().expandX()

        add(label)
        row()
        add(button)
    }

}