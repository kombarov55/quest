package com.ovt.quest.archery

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.ovt.quest.commons.unproject
import com.ovt.quest.three_in_a_row.Vector2

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
        val vCopy = Vector2(v)
        camera.unproject(vCopy)
        return vCopy
    }

    fun toWorldCoords(x: Int, y: Int): Vector2 = toWorldCoords(Vector2(x, y))
}