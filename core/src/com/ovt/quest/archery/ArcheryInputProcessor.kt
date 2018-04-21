package com.ovt.quest.archery

import com.badlogic.gdx.Input
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.ovt.quest.three_in_a_row.toRectangle

class ArcheryInputProcessor(private val screen: ArcheryScreen): GestureDetector.GestureAdapter() {

    private val directionKeys = listOf(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN)
    private val zoomKeys = listOf(Input.Keys.EQUALS, Input.Keys.MINUS)




    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        val point = screen.camera.unproject(Vector3(x, y, 0f))
        val delta = Vector2(deltaX, deltaY)
                .scl(cameraTouchMovementMultiplier, cameraTouchMovementMultiplier)
                .scl(-1f, 1f)

        screen.moveCamera.onNext(delta)
        return true
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        println(distance)
        return true
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
        val cameraMovement = 25f
        val cameraTouchMovementMultiplier = 0.7f
        val zoomDelta = 0.15f
    }

}