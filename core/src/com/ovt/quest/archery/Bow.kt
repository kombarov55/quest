package com.ovt.quest.archery

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class Bow(private val texture: Texture, val shape: Rectangle) {

    val center: Vector2 = Vector2(shape.x + shape.width/2, shape.y + shape.height/2)

    var rotation = 0f


    fun draw(sb: SpriteBatch) {
        sb.draw(texture,
                shape.x, shape.y,
                shape.width/2,
                shape.height/2,
                shape.width, shape.height,
                1f, 1f, rotation,
                0, 0,
                texture.width, texture.height,
                false, false)
    }

}