package com.ovt.quest.archery

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2

class GestureListener: GestureDetector.GestureAdapter() {

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        ArcheryEvents.moveCamera.onNext(Vector2(-deltaX, deltaY))

        return true
    }
}