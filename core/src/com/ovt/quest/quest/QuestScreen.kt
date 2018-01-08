package com.ovt.quest.quest

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.ovt.quest.commons.QuestGame

/**
 * Created by kombarov_na on 26.12.2017.
 */

class QuestScreen(internal var game: QuestGame) : Screen {

    private val stage = Stage()

    private lateinit var questNodes: List<QuestNode>

    private val optionsTable = Table()

    private val titleLabel = game.labelFactory.biggerLabel("")

    private val contentLabel = game.labelFactory.smallerLabel("")
    init {
        contentLabel.setWrap(true)
        contentLabel.setAlignment(Align.left)
    }

    override fun show() {
        initScreen()
    }

    private fun initScreen() {
        Gdx.input.inputProcessor = stage

        stage.addActor(game.background)

        addTables()

        questNodes = QuestlineParser.loadQuestNodes()

        displayNode(questNodes.first())

    }

    private fun addTables() {
        val table = Table()
        table.setFillParent(true)
        table.top()

        table.add(titleLabel)
        table.row()
        table.add(contentLabel).width(Gdx.graphics.width * 0.9f).padBottom(Gdx.graphics.height * 0.05f)
        table.row()

        table.add(optionsTable).expandY().bottom().padBottom(Gdx.graphics.height * 0.1f)

        stage.addActor(table)
    }

    fun displayNode(node: QuestNode) {
        titleLabel.setText(node.title)
        contentLabel.setText(node.content)

        optionsTable.clear()

        //TODO: переиспользовать старые кнопки
        node.options.forEach { option ->

            val optionButton = game.buttonFactory.smallerButton(option.text, {

                val nextNode = questNodes.find { it.id == option.targetId } ?: questNodes.first()
                displayNode(nextNode)


            })


            optionButton.label.setWrap(true)

            val width = Gdx.graphics.width * 0.75f
            val height = Gdx.graphics.height * 0.05f
            val pad = Gdx.graphics.width * 0.005f

            optionsTable.add(optionButton).width(width).minHeight(height).pad(pad)
            optionsTable.row()
        }

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
