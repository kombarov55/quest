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
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : ScreenAdapter() {

    private lateinit var sb: SpriteBatch
    private lateinit var imul: InputMultiplexer

    private val tilemap: TiledMap = TmxMapLoader().load("maps/archery/basic/archery-sample.tmx")
    private lateinit var tilemapRenderer: OrthogonalTiledMapRenderer
    private lateinit var camera: OrthographicCamera
    private val world: World = World(Vector2(0f, -10f), true)
    private val box2DDebugRenderer: Box2DDebugRenderer = Box2DDebugRenderer(true, true, true, true, true, true)

    private lateinit var objectFactory: ObjectFactory

    private lateinit var bow: Bow
    private lateinit var target: Target

    private lateinit var scaler: Scaler

    override fun show() {
        sb = SpriteBatch()

        camera = OrthographicCamera()
        camera.setToOrtho(false, 8f, 4.8f)

        scaler = Scaler(camera = camera)

        tilemapRenderer = OrthogonalTiledMapRenderer(tilemap, scaler.unitScale, sb)

        val tilemapHelper = TilemapHelper(tilemap, scaler)

        val zone = tilemapHelper.getZone()
        val cameraStartingPoint = tilemapHelper.getCameraStartingPoint()

        objectFactory = ObjectFactory(world, tilemapHelper)

        bow = objectFactory.createBow()
        target = objectFactory.createTarget()
        objectFactory.createGround()

        imul = InputMultiplexer(BowControlListener(zone, scaler, bow, objectFactory), CameraInputProcessor(camera), KeyInputProcessor(camera))
        Gdx.input.inputProcessor = imul

        camera.zoom = 8f
        camera.position.x = cameraStartingPoint.x
        camera.position.y = cameraStartingPoint.y
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 1f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.step(1 / 60f, 6, 2)

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