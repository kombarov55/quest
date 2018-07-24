package com.ovt.quest.commons

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class MyAnimation(texture: Texture,
                  width: Int, height: Int) {

    val regions = TextureRegion(texture).split(width, height).flatten()
    val size = regions.size

    fun getFrame(i: Int): TextureRegion {
        return if (i < size)
            regions[i] else
            regions[size - 1]
    }

}