package com.ovt.quest.commons

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.MapObject

class MySprite(val textureRegion: TextureRegion,
               var x: Float = 0f,
               var y: Float = 0f,
               var width: Float = 0f,
               var height: Float = 0f,
               var rotation: Float = 0f) {

    constructor(textureRegion: TextureRegion, obj: MapObject, PPM: Float): this(textureRegion,
            x = obj.properties["x"] as Float / PPM,
            y = obj.properties["y"] as Float / PPM,
            width = obj.properties["width"] as Float / PPM,
            height = obj.properties["height"] as Float / PPM)

    var originX: Float = width / 2
    var originY: Float = height / 2


    fun draw(sb: SpriteBatch) {
        sb.draw(textureRegion, x, y, originX, originY, width, height, 1f, 1f, rotation)
    }

}