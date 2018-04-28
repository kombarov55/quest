package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.MySprite
import com.ovt.quest.main_menu_screens.MainMenuScreen

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : Screen {

    val cam = OrthographicCamera()

    val PPM = 16f

    private val tilemap = TmxMapLoader().load("maps/archery/archery.tmx")
    private val tmxRenderer = OrthogonalTiledMapRenderer(tilemap, 1f / PPM)

    private val hudSpriteBatch = SpriteBatch()
    private val hud = Hud(game)

    private val sb = SpriteBatch()

    private val world = World(Vector2(0f, -9.81f), true)
    private val b2dr = Box2DDebugRenderer()

    private lateinit var bowSprite: MySprite
    private lateinit var targetSprite: MySprite
    private val bowTexture = TextureRegion.split(Texture(Gdx.files.internal("maps/archery/bow.png")), 70, 90)[1][5]
    private val targetTexture = TextureRegion(Texture(Gdx.files.internal("maps/archery/target.png")))

    override fun show() {
        Gdx.input.inputProcessor = InputMultiplexer(hud, KeyListener())
        cam.setToOrtho(false, 10f, 16.6f)

        tilemap.layers["graphics"].isVisible = false

        createObjects()
        makeSubscriptions()
    }

    override fun render(delta: Float) {
        world.step(1f/60, 6, 2)
        b2dr.render(world, cam.combined)

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        tmxRenderer.setView(cam)
        tmxRenderer.render()

        sb.begin()
        sb.projectionMatrix = cam.combined
        bowSprite.draw(sb)
        targetSprite.draw(sb)
        sb.end()

        hudSpriteBatch.begin()
        hud.draw(hudSpriteBatch)
        hudSpriteBatch.end()



    }

    private fun createObjects() {
        val objLayer = tilemap.layers["objects"]
        val bow = objLayer.objects["bow"]
        bowSprite = MySprite(bowTexture, bow, PPM)
        val target = objLayer.objects["target"]
        targetSprite = MySprite(targetTexture, target, PPM)

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

        Events.goHome.subscribe { game.screen = MainMenuScreen(game) }

        Events.rotateBow.subscribe { angle ->
            println("rotate at $angle")
        }

        Events.touch.subscribe {pos ->
            val pos1 = cam.unproject(Vector3(pos, 0f))

            println("x=${pos.x} y=${pos.y}")
            println("xx=${pos1.x} yy=${pos1.y}")
        }

    }

    override fun hide() { }

    override fun dispose() { }

    override fun pause() { }

    override fun resume() { }

    override fun resize(width: Int, height: Int) { }
}