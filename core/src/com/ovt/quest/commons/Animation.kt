package com.ovt.quest.commons

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.MapObject

class Animation(
        val frames: Array<TextureRegion>,
        val x: Float = 0f,
        val y: Float = 0f,
        val width: Float = 0f,
        val height: Float = 0f) {

    constructor (texture: Texture,
                 frameWidth: Int,
                 frameHeight: Int,
                 x: Float = 0f,
                 y: Float = 0f,
                 width: Float = 0f,
                 height: Float = 0f): this(TextureRegion.split(texture, frameWidth, frameHeight).flatten().toTypedArray(), x , y, width, height)

    constructor (texture: Texture, frameWidth: Int, frameHeight: Int, obj: MapObject, PPM: Float): this(texture, frameWidth, frameHeight,
            x = obj.properties["x"] as Float / PPM,
            y = obj.properties["y"] as Float / PPM,
            width = obj.properties["width"] as Float / PPM,
            height = obj.properties["height"] as Float / PPM)

    var originX: Float = width / 2
    var originY: Float = height / 2
    var rotation: Float = 0f

    var currentFrameIndex = 0

    fun nextFrame() {
        currentFrameIndex = (currentFrameIndex + 1) % frames.size
    }


    fun draw(sb: SpriteBatch) {
        sb.draw(frames[currentFrameIndex], x, y, originX, originY, width, height, 1f, 1f, rotation)
    }


}