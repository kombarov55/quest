package com.ovt.quest.archery

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.ovt.quest.commons.MyAnimation

class Bow(private val animation: MyAnimation, val shape: Rectangle) {

    val center: Vector2 = Vector2(shape.x + shape.width/2, shape.y + shape.height/2)

    var rotation = 0f
    var currentFrame: TextureRegion = animation.getFrame(0)

    //TODO: выбрать фрейм в анимации, от 0.0 до 1.0
    fun setPower(power: Float) {
        val frameIndex = (animation.size * power).toInt()
        currentFrame = animation.getFrame(frameIndex)
    }


    fun draw(sb: SpriteBatch) {
        sb.draw(currentFrame,
                shape.x, shape.y,
                shape.width/2, shape.height/2,
                shape.width, shape.height,
                1f, 1f,
                rotation)
    }

}