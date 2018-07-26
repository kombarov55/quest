package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : ScreenAdapter() {

    lateinit var sb: SpriteBatch
    lateinit var imul: InputMultiplexer

    lateinit var tilemap: TiledMap
    lateinit var tilemapRenderer: OrthogonalTiledMapRenderer
    lateinit var camera: OrthographicCamera
    lateinit var world: World
    lateinit var box2DDebugRenderer: Box2DDebugRenderer

    lateinit var objectFactory: ObjectFactory

    lateinit var bow: Bow
    lateinit var target: Target

    lateinit var scaler: Scaler


    override fun show() {
        sb = SpriteBatch()

        camera = OrthographicCamera()
        camera.setToOrtho(false, 8f, 4.8f)
        world = World(Vector2(0f, -10f), true)
        box2DDebugRenderer = Box2DDebugRenderer()

        scaler = Scaler(camera = camera)

        tilemap = TmxMapLoader().load("maps/archery/basic/archery-sample.tmx")
        tilemapRenderer = OrthogonalTiledMapRenderer(tilemap, scaler.unitScale, sb)


        objectFactory = ObjectFactory(world, tilemap, scaler)

        bow = objectFactory.createBow()
        target = objectFactory.createTarget()

        val zone = objectFactory.getZone()

        imul = InputMultiplexer(BowControlListener(zone, scaler, bow, objectFactory), CameraInputProcessor(camera), KeyInputProcessor(camera))
        Gdx.input.inputProcessor = imul
    }



    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 1f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
        tilemapRenderer.setView(camera)
        tilemapRenderer.render()
        box2DDebugRenderer.render(world, camera.combined)

        sb.begin()
        bow.draw(sb)
        target.draw(sb)
        sb.end()
    }
}