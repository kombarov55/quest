package com.ovt.quest.archery

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2

class KeyListener: InputAdapter() {

    private val camMovement = 1f
    private val zoom = 0.1f


    override fun keyDown(keycode: Int): Boolean {
        val dir = when (keycode) {
            Input.Keys.LEFT -> Vector2(-camMovement, 0f)
            Input.Keys.UP -> Vector2(0f, camMovement)
            Input.Keys.RIGHT -> Vector2(camMovement, 0f)
            Input.Keys.DOWN -> Vector2(0f, -camMovement)
            else -> null
        }

        if (dir != null) Events.moveCamera.onNext(dir)

        val zoom = when (keycode) {
            Input.Keys.MINUS -> zoom
            Input.Keys.EQUALS -> -zoom
            else -> null
        }

        if (zoom != null) Events.zoomCamera.onNext(zoom)

        return true
    }
}