package com.ovt.quest.shooting

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.ovt.quest.QuestGame
import com.ovt.quest.shooting.layout.ShootingStage

/**
 * Created by nikolay on 27/03/2018.
 */
class ShootingScreen(private val game: QuestGame) : ScreenAdapter() {

    private val w = 100f
    private val h = 160f

    private val sprite = Sprite(Texture(Gdx.files.internal("img/fon-town-1.jpg")))
    private val camera = OrthographicCamera(w / 2, h / 2)
    private val viewport = StretchViewport(camera.viewportWidth, camera.viewportHeight, camera)
    private val stage = ShootingStage(game, viewport)

    init {
        sprite.setSize(w, h)
    }

    override fun show() {
        super.show()
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.draw()
//        game.batch.begin()
//        game.batch.projectionMatrix = camera.combined
//        sprite.draw(game.batch)
//        game.batch.end()
    }
}