package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.scenes.scene2d.Action

/**
 * Created by nikolay on 15.03.18.
 */
class CallbackAction(private val callback: () -> Unit): Action() {

    override fun act(delta: Float): Boolean {
        callback.invoke()
        return true
    }
}