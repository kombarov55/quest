package com.ovt.quest.archery.pl

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3


class CameraInputProcessor(private var cam: OrthographicCamera) : InputAdapter() {

    var lastTouch = Vector3()
    var tmp = Vector3()

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        lastTouch.set(screenX.toFloat(), screenY.toFloat(), 0f)

        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        tmp.set(screenX.toFloat(), screenY.toFloat(), 0f).sub(lastTouch).scl(-1f, 1f, 0f).scl(cam.zoom).scl(1/100f, 1/100f, 0f)
        cam.translate(tmp)
        lastTouch.set(screenX.toFloat(), screenY.toFloat(), 0f)

        return false
    }

    override fun scrolled(amount: Int): Boolean {
        cam.zoom *= if (amount > 0) 1.05f else 0.95f

        return false
    }


}