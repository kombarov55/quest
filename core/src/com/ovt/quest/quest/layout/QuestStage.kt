package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.ovt.quest.QuestGame
import com.ovt.quest.main_menu_screens.MainMenuScreen

/**
 * Created by nikolay on 11.03.18.
 */
class QuestStage(private val game: QuestGame) : Stage() {

    val titleLabel = game.labelFactory.biggerLabel()
    val contentLabel = game.buttons.normalButton()

    private val diaryTable = DiaryTable(game, { hideDiary() })
    private val settingsTable = Table()
    private val optionsTable = Table()
    private val settingsButton = game.buttons.imgButton("img/settings.png", onClick = ::toggleSettings)
    private val diaryButton = game.buttons.imgButton("img/diary.png", onClick = ::showDiary)
    private val homeButton = game.buttons.imgButton("img/home.png", onClick = ::toHome)

    private var background: Image? = game.background

    private val BUTTON_SIDE_SIZE = Gdx.graphics.width * 0.07f

    init {
        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        val table = Table()
        table.setPosition(0f, 0f)
        table.width = w.toFloat()
        table.height = h.toFloat()

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

        diaryTable.isVisible = false
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

    fun clearOptions() {
        optionsTable.clear()
    }

    fun notifyDiaryNote(noteTitle: String) {
        println("Открыта запись в дневнике: $noteTitle")
    }

    //TODO: временно выключена
    fun setBackground(imgname: String) {
        println("Фон изменён на $imgname")
//        if (background?.name == imgname) return
//        actors.removeValue(background, false)
//
//        background = Image(Texture(Gdx.files.internal("img/" + imgname)))
//        background?.name = imgname
//
//        actors.insert(0, background)
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