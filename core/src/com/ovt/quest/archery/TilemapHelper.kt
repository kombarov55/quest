package com.ovt.quest.archery

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.PolygonMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.ovt.quest.commons.scale

class TilemapHelper(private val tilemap: TiledMap, private val scaler: Scaler) {


    fun getBowRectangle(): Rectangle {
        val mapObj = getMapObj(OBJECTS_LAYER, BOW)
        val rect = mapobjToRectangle(mapObj)
        rect.scale(scaler.xScale, scaler.yScale)

        return rect
    }

    fun getTargetRectangle(): Rectangle {
        val mapObj = getMapObj(OBJECTS_LAYER, TARGET)
        val rect = mapobjToRectangle(mapObj)
        rect.scale(scaler.xScale, scaler.yScale)

        return rect
    }

    fun getArrowVertices(): FloatArray {
        val mapobj = getMapObj(OBJECTS_LAYER, ARROW) as PolygonMapObject
        val vertices = mapobj.polygon.vertices

        return scaler.toWorldCoords(vertices)
    }

    fun getZone(): Rectangle {
        val mapobj = getMapObj(AREAS_LAYER, ROTATION_AREA)
        val rect = mapobjToRectangle(mapobj)
        rect.scale(scaler.xScale, scaler.yScale)

        return rect
    }

    fun getCameraStartingPoint(): Vector2 {
        val mapObj = getMapObj(OBJECTS_LAYER, CAMERA_STARTING_POINT)

        return Vector2(
                mapObj.properties.get("x", Float::class.java),
                mapObj.properties.get("y", Float::class.java))
                .scl(scaler.xScale, scaler.yScale)
    }



    private fun getMapObj(layer: String, name: String): MapObject = tilemap.layers[layer].objects[name]

    private fun mapobjToRectangle(mapObject: MapObject): Rectangle {
        val rect = Rectangle(
                mapObject.properties.get("x", Float::class.java),
                mapObject.properties.get("y", Float::class.java),
                mapObject.properties.get("width", Float::class.java),
                mapObject.properties.get("height", Float::class.java)
        )


        return rect
    }

    companion object {
        private val AREAS_LAYER = "areas"
        private val OBJECTS_LAYER = "objects"

        private val CAMERA_STARTING_POINT = "camera-starting-point"
        private val ARROW = "arrow"
        private val BOW = "bow"
        private val TARGET = "target"
        private val ROTATION_AREA = "rotation-area"
    }

}