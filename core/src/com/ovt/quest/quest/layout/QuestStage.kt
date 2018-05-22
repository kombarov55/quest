package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.addClickListener
import com.ovt.quest.main_menu_screens.MainMenuScreen
import com.ovt.quest.three_in_a_row.layout.CallbackAction

/**
 * Created by nikolay on 11.03.18.
 */
class QuestStage(private val game: QuestGame) : Stage() {

    private val table: Table

    val titleLabel = game.labelFactory.normalLabel()
    val contentLabel = game.buttons.normalButton()

    private val diaryTable = DiaryTable(game, ::hideDiary)
    private val settingsTable = Table()
    private val optionsTable = Table()
    private val settingsButton = game.buttons.imgButton("img/settings.png", onClick = ::toggleSettings)
    private val diaryButton = game.buttons.imgButton("img/diary.png", onClick = ::showDiary)
    private val homeButton = game.buttons.imgButton("img/home.png", onClick = ::toHome)

    private var background: Image? = game.background

    private val BUTTON_SIDE_SIZE = Gdx.graphics.width * 0.07f

    init {

        addActor(game.background)

        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        table = Table()
        table.setFillParent(true)
        table.top().padTop(Gdx.graphics.height * 0.03f)

        table.defaults().expandX()

        table.add(settingsTable).left()
        settingsTable.defaults().width(BUTTON_SIDE_SIZE).height(BUTTON_SIDE_SIZE)
        settingsTable.add(settingsButton)
        settingsTable.add(diaryButton)
        settingsTable.add(homeButton)

        diaryButton.isVisible = false
        homeButton.isVisible = false

        table.row()

        table.add(titleLabel)
        table.row()

        contentLabel.label.setWrap(true)
        contentLabel.label.setAlignment(Align.top)

        table.add(contentLabel)
                .width(Gdx.graphics.width * 0.9f)
                .expandX()
                .padBottom(Gdx.graphics.height * 0.05f)
                .padTop(Gdx.graphics.height * 0.03f)

        table.row()

        table.add(optionsTable).expandY().bottom().padBottom(Gdx.graphics.height * 0.1f)

        addActor(background)
        addActor(table)
        val scroll = ScrollPane(diaryTable, game.skin)
        scroll.x = diaryTable.x
        scroll.y = diaryTable.y
        scroll.width = diaryTable.width
        scroll.height = diaryTable.height
    }

    fun addOptions(options: List<String>?) {
        options?.map { option ->
            val button = game.buttons.normalButton(option, onClick = { onOptionClickListener?.invoke(option) })
            button.label.setWrap(true)
            button
        }?.forEach { button ->
            val width = Gdx.graphics.width * 0.75f
            val height = Gdx.graphics.height * 0.1f
            val pad = Gdx.graphics.width * 0.005f

            optionsTable.add(button).width(width).minHeight(height).pad(pad)
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
        println("Фон изменён на $imgname")
    }


    var onOptionClickListener: ((String) -> Unit)? = null

    private var settingsClicked = false

    private fun toggleSettings() {
        settingsClicked = !settingsClicked

        if (settingsClicked) {
            diaryButton.isVisible = true
            homeButton.isVisible = true
        } else {
            diaryButton.isVisible = false
            homeButton.isVisible = false
        }
    }

    private fun showDiary() {
        table.add(diaryTable.parent)
    }

    private fun hideDiary() {
        diaryTable.parent.remove()
        toggleSettings()
    }

    private fun toHome() {
        game.screen = MainMenuScreen(game)
    }

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