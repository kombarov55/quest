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
import com.ovt.quest.quest.commons.Globals

class QuestGame : Game() {

    lateinit var batch: SpriteBatch

    lateinit var titleLabelStyle: LabelStyle
    lateinit var descriptionStyle: LabelStyle


    lateinit var background: Image

    lateinit var buttonClickSound: Sound

    lateinit var buttons: Buttons
    lateinit var labelFactory: LabelFactory

    lateinit var globals: Globals

    internal lateinit var bigFont: BitmapFont
    internal lateinit var normalFont: BitmapFont
    internal lateinit var smallerFont: BitmapFont
    internal lateinit var tinyFont: BitmapFont

    private lateinit var textButtonSkin: Skin

    override fun create() {
        initializeVariables()

        setScreen(MainMenuScreen(this))
//        setScreen(TableTestScreen(this))
    }

    private fun initializeVariables() {
        batch = SpriteBatch()

        bigFont = createFont(Gdx.graphics.height * 0.06f)
        normalFont = createFont(Gdx.graphics.height * 0.03f)
        smallerFont = createFont(Gdx.graphics.height * 0.02f)
        tinyFont = createFont(Gdx.graphics.height * 0.008f)


        textButtonSkin = Skin(Gdx.files.internal("skin/cloud-form-ui.json"))

        buttons = Buttons(this, textButtonSkin)
        labelFactory = LabelFactory(this)

        globals = Globals()

        titleLabelStyle = LabelStyle(bigFont, Color.BLACK)
        descriptionStyle = LabelStyle(smallerFont, Color.BLACK)

        background = Image(Texture(Gdx.files.internal("img/bg.jpg")))

        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonClick.mp3"))

        Gdx.input.isCatchBackKey = true
    }

    private fun createFont(size: Int): BitmapFont {
//        val generator = FreeTypeFontGenerator(Gdx.files.internal("font/bankir-retro.ttf"))
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font/v_digital_strip.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.characters = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>"
        parameter.size = size
        parameter.color = Color.BLACK
        val bf = generator.generateFont(parameter)
        generator.dispose()

        return bf
    }

    private fun createFont(size: Float): BitmapFont = createFont(size.toInt())

    override fun dispose() {
        batch.dispose()
        bigFont.dispose()
    }

}
