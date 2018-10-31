package com.ovt.quest.archery.objects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle

class Target(private val texture: Texture, private val shape: Rectangle) {

    fun draw(sb: SpriteBatch) {
        sb.draw(texture, shape.x, shape.y, shape.width, shape.height)
    }
}