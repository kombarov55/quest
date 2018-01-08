package com.ovt.quest.commons

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

/**
 * Created by nikolay on 08/01/2018.
 */
class ButtonFactory(
        private val game: QuestGame,
        private val textButtonSkin: Skin
) {


    private lateinit var lastUsedStyle: TextButtonStyle

    fun biggerButton(text: String, callback: () -> Unit = { }): TextButton {

        switchFontBug(game.bigFont)

        val b = SoundButton(text, biggerStyle, game.buttonClickSound)

        b.addListener(object: ClickListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                callback.invoke()
            }
        })

        return b
    }

    fun normalButton(text: String): TextButton {
        switchFontBug(game.normalFont)

        return SoundButton(text, normalStyle, game.buttonClickSound)
    }


    fun smallerButton(text: String, callback: () -> Unit = { }): TextButton {
        switchFontBug(game.smallerFont)

        val b = SoundButton(text, smallerStyle, game.buttonClickSound)

        b.addListener(object: ClickListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                callback.invoke()
            }
        })

        return b
    }

    fun tinyButton(text: String): TextButton {

        switchFontBug(game.tinyFont)

        return SoundButton(text, tinyStyle, game.buttonClickSound)
    }

    private val biggerStyle = createStyle(game.bigFont, textButtonSkin)
    private val normalStyle = createStyle(game.normalFont, textButtonSkin)
    private val smallerStyle = createStyle(game.smallerFont, textButtonSkin)
    private val tinyStyle = createStyle(game.tinyFont, textButtonSkin)

    init {
        switchFontBug(biggerStyle.font)
        lastUsedStyle = biggerStyle
    }


    private fun createStyle(font: BitmapFont, skin: Skin): TextButtonStyle {
        val b = TextButton("", skin)
        val style = b.style
        style.font = font

        return style
    }

    private fun switchFontBug(font: BitmapFont) {
        val stub = TextButton("", textButtonSkin)
        stub.style.font = font
    }


}