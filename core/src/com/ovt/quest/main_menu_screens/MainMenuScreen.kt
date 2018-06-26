package com.ovt.quest.main_menu_screens


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.QuestGame
import com.ovt.quest.quest.QuestScreen

/**
 * Created by kombarov_na on 21.12.2017.
 */
class MainMenuScreen(internal var game: QuestGame) : Screen {

    private val stage = Stage()

    val t = Texture(Gdx.files.internal("img/фон-главное-меню.png"))
    val bg = Image(t)

    init {
        bg.x = 0f
        bg.y = 0f
        bg.width = Gdx.graphics.width.toFloat()
        bg.height = Gdx.graphics.height.toFloat()
    }

    override fun show() {

        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        Gdx.input.inputProcessor = stage

        val startButton = game.buttons.biggerButton("Начать", onClick = {
            clearGameProgress()
            game.screen = QuestScreen(game)
        })
        val continueButton = game.buttons.biggerButton("Продолжить", onClick = { game.screen = QuestScreen(game) })

        val minigamesButton = game.buttons.biggerButton("Миниигры", onClick = { game.screen = MinigamesScreen(game) })

        val exitButton = game.buttons.biggerButton("Выход", onClick = { Gdx.app.exit() })

        val table = Table()
        table.setFillParent(true)
        table.defaults().width(Gdx.graphics.width * 0.8f).pad(h * 0.008f)

        table.add(startButton)
        table.row()
        table.add(continueButton)
        table.row()
        table.add(minigamesButton)
        table.row()
//        table.add(exitButton)

        stage.addActor(bg)
        stage.addActor(table)
    }

    private fun clearGameProgress() {
        game.globals.currentQuestNode = game.globals.defaultQuestNode
        game.globals.questNodes.forEach { it.value.hidden = false }
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

    override fun dispose() {
        t.dispose()
    }

}
