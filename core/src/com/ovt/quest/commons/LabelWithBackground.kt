package com.ovt.quest.commons

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label

class LabelWithBackground(text: String, style: LabelStyle): Label(text, style) {

    override fun setText(newText: CharSequence?) {
        super.setText(newText)

        val pixmap = Pixmap(width.toInt(), height.toInt(), Pixmap.Format.RGB888)
        pixmap.setColor(123f, 123f, 123f, 0.6f)
        pixmap.fill()

        style.background = Image(Texture(pixmap)).drawable
    }
}