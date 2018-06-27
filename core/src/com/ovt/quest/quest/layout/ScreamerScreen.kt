package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.ovt.quest.QuestGame
import com.ovt.quest.quest.QuestScreen
import com.ovt.quest.quest.commons.Textures
import java.util.*


class ScreamerScreen(private val game: QuestGame): ScreenAdapter() {

    private val start = Date().time
    private lateinit var texture: Texture
    private lateinit var sound: Sound

    override fun show() {
        texture = Textures.getTexture("screamer.jpg")
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/screamer.mp3"))
        sound.loop(1f)
    }

    override fun render(delta: Float) {
        val currTime = Date().time
        if (currTime - start < 2000) {
            Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

            game.batch.begin()
            game.batch.draw(texture, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
            game.batch.end()

        } else {
            game.screen = QuestScreen(game)
        }
    }

    override fun hide() {
        sound.stop()
    }
}