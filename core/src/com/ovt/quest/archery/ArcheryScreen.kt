package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.Animation
import com.ovt.quest.commons.MySprite
import com.ovt.quest.main_menu_screens.MainMenuScreen

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : Screen {

    val cam = OrthographicCamera()

    val PPM = 128f

    val maxX = 50
    val maxY = 25

    private val tilemap = TmxMapLoader().load("maps/archery/hi-res/archery-hi-res.tmx")
    private val tmxRenderer = OrthogonalTiledMapRenderer(tilemap, 1f / PPM)

    private val hudSpriteBatch = SpriteBatch()
    private val hud = Hud(game)

    private val sb = SpriteBatch()

    private val world = World(Vector2(0f, -9.81f), true)
    private val b2dr = Box2DDebugRenderer()

    private lateinit var bowSprite: Animation
    private lateinit var targetSprite: MySprite
    private val targetTexture = TextureRegion(Texture(Gdx.files.internal("maps/archery/hi-res/target.png")))

    override fun show() {
        Gdx.input.inputProcessor = InputMultiplexer(hud, KeyListener(), GestureDetector(GestureListener()))
        cam.setToOrtho(false, 10f, 16.6f)

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
        bowSprite = Animation(Texture(Gdx.files.internal("maps/archery/bow-anim.png")), 70, 90, bow, PPM)
        val target = objLayer.objects["target"]
        targetSprite = MySprite(targetTexture, target, PPM)

    }

    private fun makeSubscriptions() {
        Events.moveCamera.subscribe { v ->
            if (!camOob(cam, v)) {
                cam.translate(v.x / PPM, v.y / PPM)
                cam.update()
            }
        }

        Events.zoomCamera.subscribe { z ->
            cam.zoom += z
            cam.update()
        }

        Events.goHome.subscribe { game.screen = MainMenuScreen(game) }

        Events.bowRotation.subscribe { angle ->
            bowSprite.rotation = angle
        }

        Events.touch.subscribe {pos ->
            val pos1 = cam.unproject(Vector3(pos, 0f))

            println("x=${pos.x} y=${pos.y}")
            println("xx=${pos1.x} yy=${pos1.y}")
        }

        Events.bowPower.subscribe { power ->
            val tmp = (power / Vars.bowMaxPower)
            bowSprite.currentFrameIndex = MathUtils.floor(tmp * (bowSprite.frames.size - 1).toFloat())
        }

        Events.fireBow.subscribe { (angle, power) ->
            println("fire: $angle to $power")
        }

        Events.animation.subscribe {
//            bowSprite.nextFrame()
        }

    }

    private fun camOob(cam: OrthographicCamera, delta: Vector2): Boolean {
        val newPos = Vector2(cam.position.x, cam.position.y).add(delta)

        return newPos.x - cam.viewportWidth / 2 < 0 ||
                newPos.y - cam.viewportHeight / 2 < 0 ||
                newPos.x + cam.viewportWidth / 2 > maxX ||
                newPos.y + cam.viewportHeight / 2 > maxY
    }

    override fun hide() { }

    override fun dispose() { }

    override fun pause() { }

    override fun resume() { }

    override fun resize(width: Int, height: Int) { }
}