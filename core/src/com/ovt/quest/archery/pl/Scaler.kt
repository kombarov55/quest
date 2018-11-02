package com.ovt.quest.archery.pl

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.ovt.quest.commons.unproject
import com.ovt.quest.three_in_a_row.Vector2
import com.ovt.quest.three_in_a_row.merge

/**
 * Класс, отвечающий за координаты и скалирование между кординатами экрана и мира.
 */
class Scaler(
        private val worldWidth: Float = 100f,
        private val worldHeight: Float = 50f,
        private val tilemapWidth: Float = 3200f,
        private val tilemapHeight: Float = 1600f,
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

    fun isOob(cam: OrthographicCamera): Boolean {
        return cam.viewportBottomY() <= 0 || cam.viewportTopY() >= worldHeight ||
                cam.viewportLeftX() <= 0 || cam.viewportRightX() >= worldWidth
    }

    /**
     * Выходит ли камера за границы экрана.
     *
     * @param cameraX координаты центра камеры.
     * @param cameraY координаты центра камеры.
     * @param zoom приближение камеры.
     * @param viewportWidth ширина окна камеры.
     * @param viewportHeight высота окна камеры.
     * @param worldWidth ширина мира.
     * @param worldHeight высота мира.
     */
    private fun isOob(cameraX: Float, cameraY: Float, zoom: Float, viewportWidth: Float, viewportHeight: Float, worldWidth: Float, worldHeight: Float): Boolean {
        val isBottomOob = (cameraY / zoom - viewportHeight / 2) <= 0
        val isTopOob = (cameraY / zoom + viewportHeight / 2) >= worldHeight
        val isLeftOob = (cameraX / zoom - viewportWidth / 2) <= 0
        val isRightOob = (cameraX / zoom + viewportWidth / 2) >= worldWidth

        return isBottomOob || isTopOob || isLeftOob || isRightOob
    }
}