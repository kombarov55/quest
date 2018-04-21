package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2D
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.ovt.quest.QuestGame
import com.ovt.quest.main_menu_screens.MainMenuScreen
import com.ovt.quest.three_in_a_row.Vector2
import io.reactivex.subjects.PublishSubject

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : Screen {


    val map: TiledMap = TmxMapLoader().load("maps/archery/archery.tmx")
    private val renderer: OrthogonalTiledMapRenderer = OrthogonalTiledMapRenderer(map)
    val camera = OrthographicCamera(250f, 250f)
    private val inputProcessor = ArcheryInputProcessor(this)

    val moveCamera = PublishSubject.create<Vector2>()
    val homeClicked = PublishSubject.create<Unit>()
    val zoom = PublishSubject.create<Float>()
    val bowRotation = PublishSubject.create<Float>()

    val t = Texture(Gdx.files.internal("maps/archery/bow.png"))

    val bowTexture = TextureRegion.split(t, t.width / 6, t.height / 4)[1][5]
    val bowMapObject = map.layers["objects"].objects["bow"]
    val bow = Bow(bowTexture, bowMapObject)

    val hud = ArcheryHud(game, this)

    val world = World(Vector2(0, -10), true)
    val box2dRenderer = Box2DDebugRenderer()

    override fun show() {
        Gdx.input.inputProcessor = InputMultiplexer(hud, GestureDetector(inputProcessor))
        renderer.setView(camera)
//        camera.position.set(camera.project(Vector3(bow.originX, bow.originY, 0f)))
        camera.position.set(Vector3(bow.originX, bow.originY, 0f))
        camera.update()
        renderer.setView(camera)

        moveCamera.subscribe { vector ->
            camera.translate(vector)
            camera.update()
            renderer.setView(camera)
        }
        zoom.subscribe { zoomAmount ->
            println("ZOOM")
            camera.zoom += zoomAmount
            camera.update()
            renderer.setView(camera)
        }

        homeClicked.subscribe { game.screen = MainMenuScreen(game) }

        bowRotation.subscribe { angle ->
            bow.rotation = angle
        }


        Box2D.init()


    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        renderer.render()
        renderer.batch.begin()

        bow.draw(renderer.batch)
        renderer.batch.end()


        hud.draw()

        box2dRenderer.render(world, camera.combined)
        world.step(1/60f, 6, 2)
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