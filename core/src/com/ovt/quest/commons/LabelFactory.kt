package com.ovt.quest.commons

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 08/01/2018.
 */
class LabelFactory(private val game: QuestGame) {

    private val biggerLabelStyle = LabelStyle(game.bigFont, Color.BLACK)
    private val smallerLabelStyle = LabelStyle(game.smallerFont, Color.BLACK)
    private val normalLabelStyle = LabelStyle(game.normalFont, Color.BLACK)

    fun biggerLabel(text: String = ""): Label {
        return LabelWithBackground(text, biggerLabelStyle)
    }

    fun normalLabel(text: String = ""): Label {
        return LabelWithBackground(text, normalLabelStyle)
    }

    fun smallerLabel(text: String = ""): Label {
        return LabelWithBackground(text, smallerLabelStyle)
    }

}

