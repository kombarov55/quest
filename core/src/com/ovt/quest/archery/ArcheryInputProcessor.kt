package com.ovt.quest.archery

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import io.reactivex.subjects.PublishSubject

class ArcheryInputProcessor: InputAdapter() {

    val directionTyped = PublishSubject.create<Vector2>()
    val zoomTyped = PublishSubject.create<Float>()

    private val directionKeys = listOf(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN)
    private val zoomKeys = listOf(Input.Keys.EQUALS, Input.Keys.MINUS)

    override fun keyDown(keycode: Int): Boolean {
        if (directionKeys.contains(keycode)) {
            val vector = when (keycode) {
                Input.Keys.DOWN -> Vector2(0f, -cameraMovement)
                Input.Keys.LEFT -> Vector2(-cameraMovement, 0f)
                Input.Keys.UP -> Vector2(0f, cameraMovement)
                Input.Keys.RIGHT -> Vector2(cameraMovement, 0f)
                else -> throw RuntimeException("never gonna happen")
            }
            directionTyped.onNext(vector)
        }

        else if (zoomKeys.contains(keycode)) {
            val zoomAmount = when (keycode) {
                Input.Keys.EQUALS -> -zoomDelta
                Input.Keys.MINUS -> zoomDelta
                else -> throw RuntimeException("never gonna happend")

            }
            zoomTyped.onNext(zoomAmount)
        }


        return super.keyDown(keycode)
    }

    companion object {
        private val cameraMovement = 10f
        private val zoomDelta = 0.05f
    }

}