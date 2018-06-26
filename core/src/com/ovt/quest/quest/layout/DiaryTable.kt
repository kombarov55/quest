package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.QuestGame
import com.ovt.quest.quest.commons.UIEvents

/**
 * Created by nikolay on 21/02/2018.
 */
class DiaryTable(private val game: QuestGame) : Table() {

    private val BUTTON_SIDE_SIZE = Gdx.graphics.width * 0.05f

    init {
        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        width = w * 0.8f
        height = h * 0.8f

        background = Image(Texture(Gdx.files.internal("img/settings-table-bg-white.jpg"))).drawable
        setPosition(w * 0.1f, h * 0.1f)
        defaults().expandX().width(width * 0.97f)
        top()

        val headerTable = Table()

        headerTable.defaults()
                .padTop(Gdx.graphics.height * 0.01f)
                .padBottom(Gdx.graphics.height * 0.07f)
                .top()

        headerTable.add(game.labelFactory.giantLabel("Дневник"))
                .expandX()
                .top()

        headerTable.add(game.buttons.imgButton("img/close.png", onClick = { UIEvents.toggleDiary.onNext(false) }))
                .height(BUTTON_SIDE_SIZE)
                .width(BUTTON_SIDE_SIZE)
                .right()

        add(headerTable)

        val notesTable = Table()
        notesTable.defaults().expandX().top()
        row()

        game.globals.allDiaryNotes.forEach { note ->
            val titleLabel = game.labelFactory.biggerLabel(note.title)
            notesTable.row()
            notesTable.add(titleLabel).left()


            val contentLabel = game.labelFactory.normalLabel(note.content)
            contentLabel.setWrap(true)
            notesTable.row()
            notesTable.add(contentLabel)
                    .left()
                    .width(this.width * 0.9f)
                    .minHeight(0f)
                    .pad(Gdx.graphics.height * 0.01f)
                    .padBottom(Gdx.graphics.height * 0.05f)
        }

        val sp = ScrollPane(notesTable)
        sp.width = notesTable.width
        sp.height = notesTable.height
        sp.setScrollingDisabled(true, false)
        sp.velocityY = 0.5f
        row()
        add(sp)
    }
}