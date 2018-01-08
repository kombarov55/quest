package com.ovt.quest.commons

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.ovt.quest.main_menu_screens.MainMenuScreen

class QuestGame : Game() {

    lateinit var batch: SpriteBatch

    lateinit var titleLabelStyle: LabelStyle
    lateinit var descriptionStyle: LabelStyle


    lateinit var background: Image

    lateinit var buttonClickSound: Sound

    lateinit var buttonFactory: ButtonFactory
    lateinit var labelFactory: LabelFactory


    internal lateinit var bigFont: BitmapFont
    internal lateinit var normalFont: BitmapFont
    internal lateinit var smallerFont: BitmapFont
    internal lateinit var tinyFont: BitmapFont

    private lateinit var textButtonSkin: Skin

    override fun create() {
        batch = SpriteBatch()

        bigFont = createFont(72)
        normalFont = createFont(48)
        smallerFont = createFont(36)
        tinyFont = createFont(24)


        textButtonSkin = Skin(Gdx.files.internal("skin/cloud-form-ui.json"))

        buttonFactory = ButtonFactory(this, textButtonSkin)
        labelFactory = LabelFactory(this)

        titleLabelStyle = LabelStyle(bigFont, Color.BLACK)
        descriptionStyle = LabelStyle(smallerFont, Color.BLACK)

        background = Image(Texture(Gdx.files.internal("bg.jpg")))

        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonClick.mp3"))

        setScreen(MainMenuScreen(this))
    }

    private fun createFont(size: Int): BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font/bankir-retro.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.characters = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>"
        parameter.size = size
        parameter.color = Color.BLACK
        val bf = generator.generateFont(parameter)
        generator.dispose()

        return bf
    }

    override fun dispose() {
        batch.dispose()
        bigFont.dispose()
    }

}
