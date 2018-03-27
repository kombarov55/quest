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
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.Buttons.ButtonSize.*

/**
 * Created by nikolay on 08/01/2018.
 */
class Buttons(
        private val game: QuestGame,
        private val skin: Skin
) {

    fun biggerButton(text: String = "", width: Float = 0f, height: Float = 0f, x: Float = 0f, y: Float = 0f, onClick: (() -> Unit)? = { }) = createButton(BIGGER, text, width, height, x, y ,onClick)
    fun normalButton(text: String = "", width: Float = 0f, height: Float = 0f, x: Float = 0f, y: Float = 0f, onClick: (() -> Unit)? = { }) = createButton(NORMAL, text, width, height, x, y ,onClick)
    fun smallerButton(text: String = "", width: Float = 0f, height: Float = 0f, x: Float = 0f, y: Float = 0f, onClick: (() -> Unit)? = { }) = createButton(SMALLER, text, width, height, x, y ,onClick)
    fun tinyButton(text: String = "", width: Float = 0f, height: Float = 0f, x: Float = 0f, y: Float = 0f, onClick: (() -> Unit)? = { }) = createButton(TINY, text, width, height, x, y, onClick)

    private fun createButton(size: ButtonSize, text: String, width: Float = 0f, height: Float = 0f, x: Float = 0f, y: Float = 0f, callback: (() -> Unit)? = { }): TextButton {
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

        return SoundButton(text, chosenStyle, game.buttonClickSound, callback, width, height, x, y)
    }

    fun imgButton(src: String, width: Float = 0f, height: Float = 0f, x: Float = 0f, y: Float = 0f, onClick: (() -> Unit)? = { }): ImageButton {
        val pic = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(src))))

        val b = SoundImgButton(pic, game.buttonClickSound, onClick)
        b.width = width
        b.height = height
        b.x = x
        b.y = y

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

    enum class ButtonSize {
        BIGGER, NORMAL, SMALLER, TINY
    }

}