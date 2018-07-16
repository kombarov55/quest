package com.ovt.quest.archery

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.Body

class Target(private val body: Body, private val texture: Texture, private val shape: Rectangle) {

    fun draw(sb: SpriteBatch) {
        sb.draw(texture, shape.x, shape.y, shape.width, shape.height)
    }
}