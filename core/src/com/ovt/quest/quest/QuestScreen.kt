package com.ovt.quest.quest

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.ovt.quest.commons.ButtonSize
import com.ovt.quest.commons.QuestGame
import com.ovt.quest.main_menu_screens.MainMenuScreen

/**
 * Created by kombarov_na on 26.12.2017.
 */

class QuestScreen(private val game: QuestGame) : Screen {

    private val viewport = StretchViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

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
        val table = Table()
        table.setFillParent(true)
        table.top().padTop(Gdx.graphics.height * 0.03f)

        val toHomeButton = game.buttonFactory.imgButton("img/settings.png", {
            game.screen = MainMenuScreen(game)
        })

        val buttonSideSize = 30f
        table.add(toHomeButton).left().width(buttonSideSize).height(buttonSideSize)
        table.row()

        table.add(titleLabel)
        table.row()
        table.add(contentLabel).width(Gdx.graphics.width * 0.9f).padBottom(Gdx.graphics.height * 0.05f).padTop(Gdx.graphics.height * 0.03f)
        table.row()

        table.add(optionsTable).expandY().bottom().padBottom(Gdx.graphics.height * 0.1f)

        stage.addActor(table)
    }

    private fun displayNode(node: QuestNode) {
        titleLabel.setText(node.title)
        contentLabel.setText(node.content)

        optionsTable.clear()

        //TODO: переиспользовать старые кнопки
        node.options
                .filter { game.globals.questNodes[it.targetId]?.hidden == false }
                .map { option ->

            val optionButton = game.buttonFactory.smallerButton(option.text, { optionClicked(option) })

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
