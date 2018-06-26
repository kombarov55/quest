package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ovt.quest.QuestGame
import com.ovt.quest.quest.QuestScreen


class PortraitsScreen(private val game: QuestGame): ScreenAdapter() {

    private val t: Texture
    private val img: Image
    private val camera: OrthographicCamera
    private val sb: SpriteBatch

    private val startTime = System.currentTimeMillis()

    init {
        t = Texture(Gdx.files.internal("img/портреты.png"))

        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 480f)

        sb = SpriteBatch(1)

        img = Image(t)
        img.x = 0f
        img.y = 0f
        img.width = camera.viewportWidth * 1.03f
        img.height = camera.viewportHeight
    }

    override fun show() {

    }

    override fun render(delta: Float) {
        val dt = System.currentTimeMillis() - startTime
        if (dt < 2000) {
            Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

            val x = getCameraX(dt)
            camera.position.x = x
            camera.update()
            sb.projectionMatrix = camera.combined

            sb.begin()
            img.draw(sb, 1f)
            sb.end()

        } else {
            game.screen = QuestScreen(game)
        }
    }

    override fun dispose() {
        t.dispose()
    }


    private var movementDelta = img.width - camera.viewportWidth
    private var speed = movementDelta / 2000

    fun getCameraX(dt: Long) = dt * speed + camera.viewportWidth / 2

}