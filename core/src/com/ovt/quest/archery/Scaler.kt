package com.ovt.quest.archery

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.ovt.quest.commons.unproject

class Scaler(
        val worldWidth: Float = 100f,
        val worldHeight: Float = 50f,
        val tilemapWidth: Float = 3200f,
        val tilemapHeight: Float = 1600f,
        val camera: OrthographicCamera
) {
    val unitScale = worldHeight/tilemapHeight

    val xScale = worldWidth / tilemapWidth
    val yScale = worldHeight / tilemapHeight

    fun toWorldCoords(v: Vector2): Vector2 {
        camera.unproject(v)
        return v
    }
}