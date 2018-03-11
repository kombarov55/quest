package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.ovt.quest.commons.QuestGame
import com.ovt.quest.main_menu_screens.MainMenuScreen

/**
 * Created by nikolay on 11.03.18.
 */
class QuestStage(private val game: QuestGame) : Stage() {

    val titleLabel = game.labelFactory.biggerLabel()
    val contentLabel = game.labelFactory.smallerLabel()

    private val diaryTable = DiaryTable(game, { hideDiary() })
    private val settingsTable = Table()
    private val optionsTable = Table()
    private val settingsButton = game.buttons.imgButton("img/settings.png", ::toggleSettings)
    private val diaryButton = game.buttons.imgButton("img/diary.png", ::showDiary)
    private val homeButton = game.buttons.imgButton("img/home.png", ::toHome)

    private val BUTTON_SIDE_SIZE = 40f

    init {
        addActor(game.background)

        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        val table = Table()
        table.setFillParent(true)
        table.top().padTop(Gdx.graphics.height * 0.03f)

        table.add(settingsTable).left()
        settingsTable.defaults()
                .expandX()
                .pad(w * 0.01f)
                .left()
                .width(BUTTON_SIDE_SIZE).height(BUTTON_SIDE_SIZE)
        settingsTable.add(settingsButton)
        settingsTable.add(diaryButton)
        settingsTable.add(homeButton)

        diaryButton.isVisible = false
        homeButton.isVisible = false

        table.row()

        table.add(titleLabel)
        table.row()

        contentLabel.setWrap(true)
        contentLabel.setAlignment(Align.top)
        table.add(contentLabel).width(Gdx.graphics.width * 0.9f).padBottom(Gdx.graphics.height * 0.05f).padTop(Gdx.graphics.height * 0.03f)

        table.row()

        table.add(optionsTable).expandY().bottom().padBottom(Gdx.graphics.height * 0.1f)

        addActor(table)

        diaryTable.isVisible = false
    }

    fun addOptions(options: List<String>?) {
        options?.map { option ->
            val button = game.buttons.smallerButton(option, { onOptionClickListener?.invoke(option) })
            button.label.setWrap(true)
            button
        }?.forEach { button ->
            val width = Gdx.graphics.width * 0.75f
            val height = Gdx.graphics.height * 0.05f
            val pad = Gdx.graphics.width * 0.005f

            optionsTable.add(button).width(width).minHeight(height).pad(pad)
            optionsTable.row()
        }
    }

    fun clearOptions() {
        optionsTable.clear()
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
        diaryTable.isVisible = true
    }

    private fun hideDiary() {
        diaryTable.isVisible = false
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