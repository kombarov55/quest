package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.commons.QuestGame

/**
 * Created by nikolay on 21/02/2018.
 */
class DiaryTable(private val game: QuestGame, hideClicked: () -> Unit) : Table() {

    private fun testLabel(i: Int) = game.labelFactory.normalLabel("окей $i")

    private val BUTTON_SIDE_SIZE = 30f

    init {

        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        width = w * 0.8f
        height = h * 0.8f

        background = Image(Texture(Gdx.files.internal("img/settings-table-bg-white.jpg"))).drawable
        setPosition(w * 0.1f, h * 0.1f)
        defaults().expandX().pad(h * 0.025f).width(width * 0.8f)
        top()

        add(game.buttons.imgButton("img/close.png", { hideClicked() }))
                .height(BUTTON_SIDE_SIZE).width(BUTTON_SIDE_SIZE).right()

        row()

        val diaryNotes = Table()
        diaryNotes.defaults().top().expand().pad(h * 0.025f).width(width * 0.8f)

        for (i in 0..7) {
            diaryNotes.add(testLabel(i))
            row()
        }

        val scrollPane = ScrollPane(diaryNotes)

        add(scrollPane).top().expandY()
    }
}