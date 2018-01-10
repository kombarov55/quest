package com.ovt.quest.commons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * Created by nikolay on 10/01/2018.
 */
class SoundImgButton(pic: TextureRegionDrawable, sound: Sound, callback: () -> Unit = { }) : ImageButton(pic) {

    init {
        addListener(object: ClickListener() {

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                sound.play()
                Gdx.input.vibrate(25)
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) =
                    callback.invoke()
        })
    }

}