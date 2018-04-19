package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.input.GestureDetector
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
    private val inputProcessor = ArcheryInputProcessor(map)

    val t = Texture(Gdx.files.internal("maps/archery/bow.png"))
    val bow = TextureRegion.split(t, t.width / 6, t.height / 4)[0][0]

//    private lateinit var  animation: Animation<TextureRegion>

    val bowCenter = map.layers["objects"].objects["bow"]
    val x = bowCenter.properties.get("x", Float::class.java)
    val y = bowCenter.properties.get("y", Float::class.java)
    val width = bowCenter.properties.get("width", Float::class.java)
    val height = bowCenter.properties.get("height", Float::class.java)

    override fun show() {
        renderer.setView(camera)
        Gdx.input.inputProcessor = GestureDetector(inputProcessor)
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

        camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2)

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        renderer.render()
        renderer.batch.begin()

        renderer.batch.draw(bow, x, y, width, height)
        renderer.batch.end()

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