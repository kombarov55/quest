package com.ovt.quest.archery.pl

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.ovt.quest.commons.unproject
import com.ovt.quest.three_in_a_row.Vector2
import com.ovt.quest.three_in_a_row.merge

class Scaler(
        worldWidth: Float = 100f,
        worldHeight: Float = 50f,
        tilemapWidth: Float = 3200f,
        tilemapHeight: Float = 1600f,
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

    fun toWorldCoords(vertices: FloatArray): FloatArray {
        var i = 0
        val (xVertices, yVertices) = vertices.partition { i++ % 2 == 0 }
        val transformedXVertices = xVertices.map {x -> x * xScale }
        val transformedYVertices = yVertices.map { y -> y * yScale }

        return merge(transformedXVertices, transformedYVertices).toFloatArray()
    }

    fun toWorldCoords(r: Rectangle): Rectangle {
        r.x = r.x * xScale
        r.y = r.y * yScale
        r.width = r.width * xScale
        r.height = r.height * yScale

        return r
    }
}