package com.ovt.quest.quest.layout

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.QuestGame


class ItemsTable(game: QuestGame): Table() {


    init {
        defaults().expandX().left()
        add(game.labelFactory.smallerLabel(game.globals.itemsToString()))
    }

}