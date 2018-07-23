package com.ovt.quest.archery

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.ovt.quest.three_in_a_row.Vector2

class BowRotationListener(private val zone: Rectangle, private val scaler: Scaler, private val bow: Bow): InputAdapter() {

    private var isDragging = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val p = Vector2(screenX, screenY)
        scaler.toWorldCoords(p)
        if (zone.contains(p)) {
            isDragging = true
            println(true)
            return true
        } else {
            return false
        }
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (isDragging) {
            val p = Vector2(screenX, screenY)
            scaler.toWorldCoords(p)
            val angle = getAngle(p, bow)
            bow.rotation = angle
            println("alpha: $angle")
            return true
        } else {
            return false
        }
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (isDragging) {
            isDragging = false
            return true
        } else {
            return false
        }
    }

    private fun getAngle(touch: Vector2, bow: Bow): Float {
        //float degrees = (float) ((Math.atan2(touchPoint.x - crocodile.position.x, -(touchPoint.y - crocodile.position.y)) * 180.0d / Math.PI));

        return MathUtils.atan2(touch.y - bow.center.y, touch.x - bow.center.x) * 180f / Math.PI.toFloat()

    }
}