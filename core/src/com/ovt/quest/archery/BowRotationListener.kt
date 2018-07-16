package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Rectangle
import com.ovt.quest.three_in_a_row.Vector2

class BowRotationListener(private val zone: Rectangle, private val scaler: Scaler): InputAdapter() {

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val p = Vector2(screenX, screenY)
        scaler.toWorldCoords(p)
        println(p)
        if (zone.contains(p)) {
            println(true)
        }
        return false
    }
}