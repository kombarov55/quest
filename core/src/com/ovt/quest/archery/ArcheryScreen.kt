package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : Screen {

    val cam = OrthographicCamera()

    private val tilemap = TmxMapLoader().load("maps/archery/archery.tmx")
    private val tmxRenderer = OrthogonalTiledMapRenderer(tilemap, 1f / 16)


    override fun show() {
        Gdx.input.inputProcessor = InputMultiplexer(KeyListener())
        cam.setToOrtho(false, 10f, 16.6f)

        makeSubscriptions()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        tmxRenderer.setView(cam)
        tmxRenderer.render()
    }

    private fun makeSubscriptions() {
        Events.moveCamera.subscribe { v ->
            cam.translate(v)
            cam.update()
        }

        Events.zoomCamera.subscribe { z ->
            cam.zoom += z
            cam.update()
        }

    }

    override fun hide() { }

    override fun dispose() { }

    override fun pause() { }

    override fun resume() { }

    override fun resize(width: Int, height: Int) { }
}