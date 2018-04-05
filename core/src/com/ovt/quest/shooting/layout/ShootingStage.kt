package com.ovt.quest.shooting.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.Viewport
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 27/03/2018.
*/
class ShootingStage(private val game: QuestGame, viewport: Viewport) : Stage(viewport, game.batch) {

    private val background = Image(TextureRegion(Texture(Gdx.files.internal("img/fon-town-1.jpg"))))
    private val camera = OrthographicCamera()

    init {
        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        addActor(background)
        background.x = 0f
        background.y = 0f
        background.width = 100f
        background.height = 160f
        viewport.camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)
        viewport.camera.update()
    }

    private var touchDown: Vector2? = null

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        touchDown = viewport.unproject(Vector2(screenX.toFloat(), screenY.toFloat()))
        viewport.camera.translate(1f, 0f, 0f)
        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val drag = viewport.unproject(Vector2(screenX.toFloat(), screenY.toFloat()))

        val dx = touchDown!!.x - drag.x
        val dy = touchDown!!.y - drag.y
        viewport.camera.translate(dx, dy, 0f)

        return super.touchDragged(screenX, screenY, pointer)
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        touchDown = null
        return super.touchUp(screenX, screenY, pointer, button)
    }
}