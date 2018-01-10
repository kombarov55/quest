package com.ovt.quest.commons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle

/**
 * Created by nikolay on 08/01/2018.
 */
class ButtonFactory(
        private val game: QuestGame,
        private val skin: Skin
) {

    fun biggerButton(text: String, callback: () -> Unit = { }): TextButton {
        switchFontBug(game.bigFont)
        return SoundButton(text, biggerStyle, game.buttonClickSound, callback)
    }

    fun normalButton(text: String, callback: () -> Unit = { }): TextButton {
        switchFontBug(game.normalFont)
        return SoundButton(text, normalStyle, game.buttonClickSound, callback)
    }


    fun smallerButton(text: String, callback: () -> Unit = { }): TextButton {
        switchFontBug(game.smallerFont)
        return SoundButton(text, smallerStyle, game.buttonClickSound, callback)
    }

    fun tinyButton(text: String, callback: () -> Unit = { }): TextButton {
        switchFontBug(game.tinyFont)
        return SoundButton(text, tinyStyle, game.buttonClickSound, callback)
    }

    fun imgButton(src: String, width: Float, height: Float, callback: () -> Unit = { }): Image {
        val i = SoundImgButton(Texture(Gdx.files.internal(src)), game.buttonClickSound, callback)
        i.setSize(width, height)
        return i
    }

    private val biggerStyle = createStyle(game.bigFont, skin)
    private val normalStyle = createStyle(game.normalFont, skin)
    private val smallerStyle = createStyle(game.smallerFont, skin)
    private val tinyStyle = createStyle(game.tinyFont, skin)


    private fun createStyle(font: BitmapFont, skin: Skin): TextButtonStyle {
        val b = TextButton("", skin)
        val style = b.style
        style.font = font

        return style
    }

    private fun switchFontBug(font: BitmapFont) {
        val stub = TextButton("", skin)
        stub.style.font = font
    }


}