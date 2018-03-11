package com.ovt.quest.commons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.ovt.quest.commons.ButtonSize.*

/**
 * Created by nikolay on 08/01/2018.
 */
class Buttons(
        private val game: QuestGame,
        private val skin: Skin
) {

    fun biggerButton(text: String, callback: (() -> Unit)? = { }) = createButton(BIGGER, text, callback)
    fun normalButton(text: String, callback: (() -> Unit)? = { }) = createButton(NORMAL, text, callback)
    fun smallerButton(text: String, callback: (() -> Unit)? = { }) = createButton(SMALLER, text, callback)
    fun tinyButton(text: String, callback: (() -> Unit)? = { }) = createButton(TINY, text, callback)

    private fun createButton(size: ButtonSize, text: String, callback: (() -> Unit)? = { }): TextButton {
        val chosenFont = when (size) {
            BIGGER -> game.bigFont
            NORMAL -> game.normalFont
            SMALLER -> game.smallerFont
            TINY -> game.tinyFont
        }

        val chosenStyle = when(size) {
            BIGGER -> biggerStyle
            NORMAL -> normalStyle
            SMALLER -> smallerStyle
            TINY -> tinyStyle
        }

        switchFontBug(chosenFont)

        return SoundButton(text, chosenStyle, game.buttonClickSound, callback)
    }

    fun imgButton(src: String, callback: (() -> Unit)? = { }): ImageButton {
        val pic = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(src))))

        val b = SoundImgButton(pic, game.buttonClickSound, callback)

        return b
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

enum class ButtonSize {
    BIGGER, NORMAL, SMALLER, TINY
}