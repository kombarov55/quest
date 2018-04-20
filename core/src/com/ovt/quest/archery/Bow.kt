package com.ovt.quest.archery

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.MapObject
import com.ovt.quest.three_in_a_row.toRectangle

class Bow(
        private val region: TextureRegion,
        var x: Float = 0f,
        var y: Float = 0f,
        var width: Float = region.regionWidth.toFloat(),
        var height: Float = region.regionHeight.toFloat()
) {

    var originX: Float = x + (width / 2)
    var originY: Float = y + (height / 2)
    var scaleX: Float = 1f
    var scaleY: Float = 1f
    var rotation: Float = 0f

    constructor(region: TextureRegion, mapObject: MapObject): this(region) {
        val r = toRectangle(mapObject)
        x = r.x
        y = r.y
        width = r.width
        height = r.height
    }


    fun draw(batch: Batch?) {
        batch?.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

}