package com.ovt.quest.archery

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.ovt.quest.commons.height
import com.ovt.quest.commons.scale
import com.ovt.quest.three_in_a_row.Rectangle

class ObjectFactory(private val world: World,
                    private val tilemap: TiledMap,
                    private val scaleX: Float,
                    private val scaleY: Float) {




    fun createBow(): Bow {
        val mapobj = tilemap.layers["objects"].objects["bow"]
        val body = mapObjToBox(mapobj)
        return Bow(body)
    }

    fun createTarget(): Target {
        val mapobj = tilemap.layers["objects"].objects["target"]
        val body = mapObjToBox(mapobj)
        return Target(body)
    }

    fun mapObjToBox(mapobj: MapObject): Body {
        val rect = Rectangle(mapobj)
        rect.scale(scaleX, scaleY)
        rect.y += rect.height/2

        val bdef = BodyDef()
        bdef.position.set(rect.x, rect.y)
        val body = world.createBody(bdef)

        val shape = PolygonShape()
        shape.setAsBox(rect.width / 2, rect.height / 2)
        body.createFixture(shape, 0.5f)

        return body
    }

}