package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : Screen {


    private val map: TiledMap = TmxMapLoader().load("maps/archery/archery.tmx")
    private val renderer: OrthogonalTiledMapRenderer = OrthogonalTiledMapRenderer(map)
    private val camera = OrthographicCamera(1000f, 1000f)
    private val inputProcessor = ArcheryInputProcessor()

    override fun show() {
        renderer.setView(camera)
        Gdx.input.inputProcessor = inputProcessor
        inputProcessor.moveCamera.subscribe { vector ->
            camera.translate(vector)
            camera.update()
            renderer.setView(camera)
        }
        inputProcessor.zoom.subscribe { zoomAmount ->
            camera.zoom += zoomAmount
            camera.update()
            renderer.setView(camera)
        }

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        renderer.render()
    }

    override fun hide() { }

    override fun dispose() {
        map.dispose()
        renderer.dispose()
    }

    override fun pause() { }

    override fun resume() { }

    override fun resize(width: Int, height: Int) { }
}