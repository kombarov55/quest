package com.ovt.quest.archery

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import io.reactivex.subjects.PublishSubject

class ArcheryInputProcessor: InputAdapter() {

    val moveCamera = PublishSubject.create<Vector2>()
    val zoom = PublishSubject.create<Float>()

    private val directionKeys = listOf(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN)
    private val zoomKeys = listOf(Input.Keys.EQUALS, Input.Keys.MINUS)

    private var lastTouch = Vector2()

    override fun keyDown(keycode: Int): Boolean {
        if (directionKeys.contains(keycode)) {
            moveCamera.onNext(translateKeyDirection(keycode))
        }

        else if (zoomKeys.contains(keycode)) {
            zoom.onNext(translateZoom(keycode))
        }
        return super.keyDown(keycode)
    }



    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        lastTouch.x = screenX.toFloat()
        lastTouch.y = screenY.toFloat()
        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        lastTouch.x = 0f
        lastTouch.y = 0f
        return super.touchUp(screenX, screenY, pointer, button)
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {

        val x = screenX.toFloat()
        val y = screenY.toFloat()

        val delta = Vector2(lastTouch)
                .sub(x, y)
                .scl(cameraTouchMovementMultiplier, cameraTouchMovementMultiplier)
                .scl(1f, -1f)

        lastTouch.x = x
        lastTouch.y = y

        moveCamera.onNext(delta)

        return super.touchDragged(screenX, screenY, pointer)
    }

    private fun translateKeyDirection(keycode: Int): Vector2 {
        return when (keycode) {
            Input.Keys.DOWN -> Vector2(0f, -cameraMovement)
            Input.Keys.LEFT -> Vector2(-cameraMovement, 0f)
            Input.Keys.UP -> Vector2(0f, cameraMovement)
            Input.Keys.RIGHT -> Vector2(cameraMovement, 0f)
            else -> throw RuntimeException("never gonna happen")
        }
    }

    private fun translateZoom(keycode: Int): Float {
        return when (keycode) {
            Input.Keys.EQUALS -> -zoomDelta
            Input.Keys.MINUS -> zoomDelta
            else -> throw RuntimeException("never gonna happen")
        }
    }

    companion object {
        private val cameraMovement = 25f
        private val cameraTouchMovementMultiplier = 1f
        private val zoomDelta = 0.05f
    }

}