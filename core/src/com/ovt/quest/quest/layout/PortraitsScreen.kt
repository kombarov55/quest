package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ovt.quest.QuestGame
import com.ovt.quest.quest.QuestScreen


class PortraitsScreen(private val game: QuestGame): ScreenAdapter() {

    private lateinit var t: Texture
    private lateinit var img: Image

    private val startTime = System.currentTimeMillis()

    override fun show() {
        t = Texture(Gdx.files.internal("img/портреты.png"))
        img = Image(t)

        img.x = 0f
        img.y = 0f
        img.width = Gdx.graphics.width.toFloat()
        img.height = Gdx.graphics.height.toFloat()
    }

    override fun render(delta: Float) {
        val totalTime = System.currentTimeMillis() - startTime
        if (totalTime < 2000) {
            game.batch.begin()
            img.draw(game.batch, 1f)
            game.batch.end()
        } else {
            game.screen = QuestScreen(game)
        }
    }

    override fun dispose() {

    }
}