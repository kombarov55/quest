package com.ovt.quest.quest

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.ovt.quest.commons.QuestGame
import com.ovt.quest.main_menu_screens.MainMenuScreen

/**
 * Created by kombarov_na on 26.12.2017.
 */

class QuestScreen(private val game: QuestGame) : Screen {

    private val stage = Stage()

    private lateinit var questNodes: List<QuestNode>

    private val optionsTable = Table()

    private val titleLabel = game.labelFactory.biggerLabel("")

    private val contentLabel = game.labelFactory.smallerLabel("")
    init {
        contentLabel.setWrap(true)
        contentLabel.setAlignment(Align.top)
    }

    override fun show() {
        initScreen()
    }

    private fun initScreen() {
        Gdx.input.inputProcessor = stage

        stage.addActor(game.background)

        addWidgets()

        questNodes = QuestlineLoader.loadQuestNodes()

        displayNode(questNodes.first())

    }

    private fun addWidgets() {
        val table = Table()
        table.setFillParent(true)
        table.top().padTop(Gdx.graphics.height * 0.03f)

        val buttonSideSize = minOf(Gdx.graphics.height, Gdx.graphics.width) * 0.01f
        val toHomeButton = game.buttonFactory.imgButton("img/home.png", buttonSideSize, buttonSideSize, {
            game.screen = MainMenuScreen(game)
        })
        table.add(toHomeButton).left()
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
        node.options.map { option ->

            val optionButton = game.buttonFactory.smallerButton(option.text, {
                val nextNode = questNodes.find { it.id == option.targetId } ?: questNodes.first()
                displayNode(nextNode)
            })

            optionButton.label.setWrap(true)

            optionButton

        }.forEach { optionButton ->

            val width = Gdx.graphics.width * 0.75f
            val height = Gdx.graphics.height * 0.05f
            val pad = Gdx.graphics.width * 0.005f

            optionsTable.add(optionButton).width(width).minHeight(height).pad(pad)
            optionsTable.row()

        }


//
//        }.forEach { }
//
//        forEach { option ->
//
//            val optionButton = game.buttonFactory.smallerButton(option.text, {
//
//                val nextNode = questNodes.find { it.id == option.targetId } ?: questNodes.first()
//                displayNode(nextNode)
//
//
//            })
//
//
//            optionButton.label.setWrap(true)
//
//            val width = Gdx.graphics.width * 0.75f
//            val height = Gdx.graphics.height * 0.05f
//            val pad = Gdx.graphics.width * 0.005f
//
//            optionsTable.add(optionButton).width(width).minHeight(height).pad(pad)
//            optionsTable.row()
//        }

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
