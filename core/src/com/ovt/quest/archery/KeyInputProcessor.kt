package com.ovt.quest.archery

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera

class KeyInputProcessor(private val camera: OrthographicCamera): InputAdapter() {

    override fun keyDown(keycode: Int): Boolean {
        if(keycode == Input.Keys.LEFT)
            camera.translate(-32f,0f)
        if(keycode == Input.Keys.RIGHT)
            camera.translate(32f,0f)
        if(keycode == Input.Keys.UP)
            camera.translate(0f,-32f)
        if(keycode == Input.Keys.DOWN)
            camera.translate(0f,32f)
        return true
    }
}