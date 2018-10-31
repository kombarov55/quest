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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.ovt.quest.QuestGame
import com.ovt.quest.archery.box2d.ArcheryContactListener
import com.ovt.quest.archery.events.Events
import com.ovt.quest.archery.events.Subscriptions
import com.ovt.quest.archery.events.Vars
import com.ovt.quest.archery.events.dto.GroundHitDto
import com.ovt.quest.archery.events.dto.TargetHitDto
import com.ovt.quest.archery.objects.Bow
import com.ovt.quest.archery.objects.ObjectFactory
import com.ovt.quest.archery.objects.Target
import com.ovt.quest.archery.objects.TilemapHelper
import com.ovt.quest.archery.pl.*
import com.ovt.quest.archery.pl.CameraMovementType.FOLLOWING_ARROW

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
        objectFactory.createTargetCollisionLine()

        imul = InputMultiplexer(TouchDownListener(), BowControlListener(zone, scaler, bow), CameraInputProcessor(camera), KeyInputProcessor(camera))
        Gdx.input.inputProcessor = imul

        camera.zoom = 8f
        camera.position.x = cameraStartingPoint.x
        camera.position.y = cameraStartingPoint.y

        val subscriptions = Subscriptions(objectFactory)
        subscriptions.makeSubscriptions()

        world.setContactListener(ArcheryContactListener())
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 1f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.step(1 / 60f, 6, 2)

        if (Vars.arrowAndTargetContact != null) {
            val contact = Vars.arrowAndTargetContact!!
            Events.targetHit.onNext(TargetHitDto(contact))
            Vars.arrowAndTargetContact = null
        }

        if (Vars.arrowAndGroundContact != null) {
            val contact = Vars.arrowAndGroundContact!!
            Events.groundHit.onNext(GroundHitDto(contact))
            Vars.arrowAndGroundContact = null
        }

        cameraMovement()
        camera.update()
        tilemapRenderer.setView(camera)
        tilemapRenderer.render()

        sb.begin()
        bow.draw(sb)
        target.draw(sb)
        sb.end()

        box2DDebugRenderer.render(world, camera.combined)
    }

    private fun cameraMovement() {
        if (Vars.cameraMovementType == FOLLOWING_ARROW) {
            camera.position.set(Vector2(Vars.arrow!!.position), 0f)
        }
    }
}