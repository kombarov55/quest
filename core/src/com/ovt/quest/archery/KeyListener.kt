package com.ovt.quest.archery

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2

class KeyListener: InputAdapter() {




    override fun keyDown(keycode: Int): Boolean {
        val dir = when (keycode) {
            Input.Keys.LEFT -> Vector2(-Vars.camMovement, 0f)
            Input.Keys.UP -> Vector2(0f, Vars.camMovement)
            Input.Keys.RIGHT -> Vector2(Vars.camMovement, 0f)
            Input.Keys.DOWN -> Vector2(0f, -Vars.camMovement)
            else -> null
        }

        if (dir != null) Events.moveCamera.onNext(dir)

        val zoom = when (keycode) {
            Input.Keys.MINUS -> Vars.zoom
            Input.Keys.EQUALS -> -Vars.zoom
            else -> null
        }

        if (zoom != null) Events.zoomCamera.onNext(zoom)

        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        println("x=$screenX y=$screenY")
        return false
    }
}