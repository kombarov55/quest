package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import io.reactivex.subjects.PublishSubject

class ArcheryInputProcessor(private val map: TiledMap): GestureDetector.GestureAdapter() {

    val moveCamera = PublishSubject.create<Vector2>()
    val zoom = PublishSubject.create<Float>()

    private val directionKeys = listOf(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN)
    private val zoomKeys = listOf(Input.Keys.EQUALS, Input.Keys.MINUS)

    private val bowArea = Rectangle()


    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        val delta = Vector2(deltaX, deltaY)
                .scl(cameraTouchMovementMultiplier, cameraTouchMovementMultiplier)
                .scl(-1f, 1f)

        moveCamera.onNext(delta)

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
        private val cameraMovement = 25f
        private val cameraTouchMovementMultiplier = 1f
        private val zoomDelta = 0.05f
    }

}