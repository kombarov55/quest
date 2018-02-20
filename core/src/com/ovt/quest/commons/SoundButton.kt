package com.ovt.quest.commons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

/**
 * Created by nikolay on 08/01/2018.
 */
class SoundButton(text: String,
                  style: TextButtonStyle,
                  clickingSound: Sound, callback: () -> Unit = { }) : TextButton(text, style) {

    init {
        addListener(object: ClickListener() {

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                clickingSound.play()
                Gdx.input.vibrate(25)

                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) =
                    callback.invoke()

        })
    }
}