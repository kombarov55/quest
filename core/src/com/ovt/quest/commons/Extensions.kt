package com.ovt.quest.commons

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener


/**
 * Created by nikolay on 15.03.18.
 */
fun Actor.addClickListener(f: () -> Unit) {
    addListener(object : ClickListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            f.invoke()
            return super.touchDown(event, x, y, pointer, button)
        }
    })
}