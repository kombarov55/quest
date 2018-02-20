package com.ovt.quest.quest

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.ovt.quest.commons.QuestGame
import com.ovt.quest.main_menu_screens.MainMenuScreen

/**
 * Created by kombarov_na on 26.12.2017.
 */

class QuestScreen(private val game: QuestGame) : Screen {

    private val viewport = StretchViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

    private val settingsButton = game.buttons.imgButton("img/settings.png", { toggleSettings() })
    private val diaryButton = game.buttons.imgButton("img/diary.png", { showDiary() })
    private val homeButton = game.buttons.imgButton("img/home.png", { toHome() })

    private val diaryTable = DiaryTable(game, { hideDiary() })

    private val settingsTable = Table()

    private val BUTTON_SIDE_SIZE = 40f

    private val stage = object : Stage(viewport) {
        override fun keyDown(keyCode: Int): Boolean =
                when (keyCode) {
                    Input.Keys.BACK -> {
                        game.screen = MainMenuScreen(game)
                        true
                    }

                    else -> super.keyDown(keyCode)
                }
    }

    private val optionsTable = Table()
    private val titleLabel = game.labelFactory.biggerLabel("")
    private val contentLabel = game.labelFactory.smallerLabel("")
    init {
        contentLabel.setWrap(true)
        contentLabel.setAlignment(Align.top)
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
        Gdx.input.isCatchBackKey = true

        stage.addActor(game.background)

        addWidgets()

        displayNode(game.globals.currentQuestNode)
    }



    private fun addWidgets() {
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
        table.add(contentLabel).width(Gdx.graphics.width * 0.9f).padBottom(Gdx.graphics.height * 0.05f).padTop(Gdx.graphics.height * 0.03f)
        table.row()

        table.add(optionsTable).expandY().bottom().padBottom(Gdx.graphics.height * 0.1f)

        stage.addActor(table)

        stage.addActor(diaryTable)
        diaryTable.isVisible = false
    }

    private fun displayNode(node: QuestNode) {
        titleLabel.setText(node.title)
        contentLabel.setText(node.content)

        optionsTable.clear()

        //TODO: переиспользовать старые кнопки
        node.options
                .filter { game.globals.questNodes[it.targetId]?.hidden == false }
                .map { option ->

            val optionButton = game.buttons.smallerButton(option.text, { optionClicked(option) })

            optionButton.label.setWrap(true)

            optionButton

        }.forEach { optionButton ->

            val width = Gdx.graphics.width * 0.75f
            val height = Gdx.graphics.height * 0.05f
            val pad = Gdx.graphics.width * 0.005f

            optionsTable.add(optionButton).width(width).minHeight(height).pad(pad)
            optionsTable.row()

        }

    }

    private fun optionClicked(option: Option) {
        val actionResult = executeAction(option.action)

        setNextNode(option.targetId, actionResult)

        displayNode(game.globals.currentQuestNode)
    }

    private fun executeAction(actionName: String?): String? =
        if (actionName != null)
            QuestActions.actions[actionName]?.invoke(game) else
            null

    private fun setNextNode(targetId: String?, actionResult: String?) {
        val nextId = targetId ?: actionResult
        game.globals.currentQuestNode = game.globals.questNodes[nextId] ?: game.globals.defaultQuestNode
    }

    private fun toHome() {
        game.screen = MainMenuScreen(game)
    }

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
    }



    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.draw()
    }

    override fun resize(width: Int, height: Int) {}

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun dispose() {}
}
