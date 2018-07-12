package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.objects.PolygonMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.*
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.Animation
import com.ovt.quest.commons.MySprite
import com.ovt.quest.main_menu_screens.MainMenuScreen

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryScreen(private val game: QuestGame) : ScreenAdapter() {

    lateinit var sb: SpriteBatch

    lateinit var tilemap: TiledMap
    lateinit var tilemapRenderer: OrthogonalTiledMapRenderer
    lateinit var camera: OrthographicCamera

    override fun show() {

        sb = SpriteBatch()

        tilemap = TmxMapLoader().load("maps/archery/basic/archery-sample.tmx")
        tilemapRenderer = OrthogonalTiledMapRenderer(tilemap, sb)
        camera = OrthographicCamera()
        camera.setToOrtho(false, 1600f, 1600f)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        tilemapRenderer.setView(camera)
        tilemapRenderer.render()
    }
}