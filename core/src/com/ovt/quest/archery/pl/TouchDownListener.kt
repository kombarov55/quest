package com.ovt.quest.archery.pl

import com.badlogic.gdx.InputAdapter
import com.ovt.quest.archery.events.Events
import com.ovt.quest.archery.events.dto.TouchDownDto

/**
 * InputProcessor для возбуждения события touchDown
 */
class TouchDownListener : InputAdapter() {

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        //FIXME: передаются координаты с экрана, может ещё и Y-сверху
        Events.touchDown.onNext(TouchDownDto(screenX.toFloat(), screenY.toFloat()))
        return false
    }
}