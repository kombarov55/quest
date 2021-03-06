package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.ovt.quest.QuestGame
import com.ovt.quest.main_menu_screens.MainMenuScreen
import com.ovt.quest.quest.commons.Textures
import com.ovt.quest.quest.commons.UIEvents
import com.ovt.quest.three_in_a_row.layout.CallbackAction

/**
 * Created by nikolay on 11.03.18.
 */
class QuestStage(private val game: QuestGame) : Stage() {

    private val table: Table

    val titleLabel = game.labelFactory.normalLabel()
    val contentLabel = game.buttons.normalButton()
    val itemsLabel = game.labelFactory.smallerLabel()

    private val diaryTable = DiaryTable(game)
    private val optionsTable = Table()

    init {

        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        table = Table()
        table.setFillParent(true)
        table.top().padTop(Gdx.graphics.height * 0.03f)

        table.defaults().expandX()

        table.add(SettingsTable(game)).left().padLeft(w * 0.01f)
        table.row()

        contentLabel.label.setWrap(true)
        contentLabel.label.setAlignment(Align.top)

        table.add(contentLabel).width(w * 0.9f).padBottom(h * 0.05f).padTop(h * 0.03f)
        table.row()

        optionsTable.defaults().width(w * 0.7f).minHeight(h * 0.1f).pad(w * 0.002f)
        table.add(optionsTable).expandY().bottom().padBottom(Gdx.graphics.height * 0.03f)
        table.row()

        itemsLabel.setText(game.globals.itemsToString())
        table.add(itemsLabel).left()

        addActor(game.globals.currentBg)
        addActor(table)

        subscribeToggleDiary()
        subscribeHomePressed()
    }

    fun addOptions(options: List<String>?) {
        options?.map { option ->
            val button = game.buttons.normalButton(option, onClick = { onOptionClickListener?.invoke(option) })
            button.label.setWrap(true)
            button
        }?.forEach { button ->
            optionsTable.add(button)
            optionsTable.row()
        }
    }

    fun showToast(text: String) {
        val toastLabel = game.labelFactory.smallerLabel(text)
        toastLabel.x = Gdx.graphics.width / 2 - toastLabel.width / 2
        toastLabel.y = Gdx.graphics.height * 0.4f
        addActor(toastLabel)
        toastLabel.addAction(SequenceAction(
                ParallelAction(
                        Actions.fadeOut(2f),
                        Actions.moveBy(0f, Gdx.graphics.height * 0.02f, 2f)),
                CallbackAction { toastLabel.remove() }))
    }

    fun clearOptions() {
        optionsTable.clear()
    }

    fun notifyDiaryNote(noteTitle: String) {
        val msg = "Открыта запись в дневнике: $noteTitle"
        showToast(msg)
        println(msg)
    }

    fun setBackground(imgname: String) {
        game.globals.currentBg.drawable = TextureRegionDrawable(TextureRegion(Textures.getTexture(imgname)))
    }

    fun updateItemsLine() {
        itemsLabel.setText(game.globals.itemsToString())
    }


    private fun subscribeToggleDiary() {
        UIEvents.toggleDiary.subscribe { show ->
            if (show) {
                addActor(diaryTable)
            } else {
                diaryTable.remove()
            }
        }
    }

    private fun subscribeHomePressed() {
        UIEvents.homePressed.subscribe {
            game.screen = MainMenuScreen(game)
        }
    }

    var onOptionClickListener: ((String) -> Unit)? = null

    override fun keyDown(keyCode: Int): Boolean {
        return when (keyCode) {
            Input.Keys.BACK -> {
                game.screen = MainMenuScreen(game)
                true
            }

            else -> super.keyDown(keyCode)
        }
    }
}