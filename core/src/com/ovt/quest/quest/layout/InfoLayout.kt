package com.ovt.quest.quest.layout

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.QuestGame


class InfoLayout(game: QuestGame): Table() {


    init {
        defaults().expandX().left()
        add(game.labelFactory.smallerLabel("У вас имеется: 5 монет, Башня"))
    }

}