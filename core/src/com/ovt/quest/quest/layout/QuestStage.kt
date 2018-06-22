package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.Res
import com.ovt.quest.main_menu_screens.MainMenuScreen
import com.ovt.quest.quest.commons.Items
import com.ovt.quest.three_in_a_row.layout.CallbackAction

/**
 * Created by nikolay on 11.03.18.
 */
class QuestStage(private val game: QuestGame) : Stage() {

    private val table: Table

    val titleLabel = game.labelFactory.normalLabel()
    val contentLabel = game.buttons.normalButton()

    private val diaryTable = DiaryTable(game, ::hideDiary)
    private val settingsTable: Table
    private val itemsTable = Table()
    private val optionsTable = Table()
    private lateinit var settingsButton: ImageButton
    private lateinit var  diaryButton: ImageButton
    private lateinit var  homeButton: ImageButton

    private var background: Image? = game.background

    private val BUTTON_SIDE_SIZE = Gdx.graphics.width * 0.05f

    init {

        addActor(game.background)

        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        table = Table()
        table.debug = true
        table.setFillParent(true)
        table.top().padTop(Gdx.graphics.height * 0.03f)

        table.defaults().expandX()


        settingsTable = createSettingsTable()
        table.add(settingsTable).left().padLeft(w * 0.01f)
        table.row()

        contentLabel.label.setWrap(true)
        contentLabel.label.setAlignment(Align.top)

        table.add(contentLabel)
                .width(Gdx.graphics.width * 0.9f)
                .expandX()
                .padBottom(Gdx.graphics.height * 0.05f)
                .padTop(Gdx.graphics.height * 0.03f)

        table.row()

        /*
        val width = Gdx.graphics.width * 0.75f
            val height = Gdx.graphics.height * 0.1f
            val pad = Gdx.graphics.width * 0.005f

        .width(width).minHeight(height).pad(pad)
         */

        optionsTable.defaults().width(w * 0.5f).height(h * 0.07f).pad(w * 0.002f)

        table.add(optionsTable).expandY().bottom().padBottom(Gdx.graphics.height * 0.1f)

        addActor(background)
        addActor(table)
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
        addActor(diaryTable)
    }

    private fun hideDiary() {
        diaryTable.remove()
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

    // tables

    fun createSettingsTable(): Table {
        val settingsTable = Table()

        settingsButton = game.buttons.imgButton("img/settings.png", onClick = ::toggleSettings)
        diaryButton = game.buttons.imgButton("img/diary.png", onClick = ::showDiary)
        homeButton = game.buttons.imgButton("img/home.png", onClick = ::toHome)

        diaryButton.isVisible = false
        homeButton.isVisible = false

        settingsTable.defaults().width(BUTTON_SIDE_SIZE).height(BUTTON_SIDE_SIZE).expandY().fillY()
        settingsTable.add(settingsButton)
        settingsTable.add(diaryButton)
        settingsTable.add(homeButton)

        return settingsTable
    }




}