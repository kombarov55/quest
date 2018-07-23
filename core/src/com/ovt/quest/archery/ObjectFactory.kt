package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.ovt.quest.commons.scale

class ObjectFactory(private val world: World,
                    private val tilemap: TiledMap,
                    private val scaler: Scaler) {




    fun createBow(): Bow {
        val mapobj = tilemap.layers["objects"].objects["bow"]
        val r = mapobjToRectangle(mapobj)
        //TODO: сделать загрузчик текстур
        val t = Texture(Gdx.files.internal("maps/archery/bow-rescaled.png"))
        return Bow(t, r)
    }

    fun createTarget(): Target {
        val mapobj = tilemap.layers["objects"].objects["target"]
        val r = mapobjToRectangle(mapobj)
        val t = Texture(Gdx.files.internal("maps/archery/target-rescaled.png"))
        return Target(t, r)
    }

    fun getZone(): Rectangle {
        val mapobj = tilemap.layers["areas"].objects["rotation-area"]
        return mapobjToRectangle(mapobj)
    }

    private fun rectangleToBody(rect: Rectangle): Body {
        val bdef = BodyDef()
        bdef.position.set(rect.x + rect.width/2, rect.y + rect.height/2)
        val body = world.createBody(bdef)

        val shape = PolygonShape()
        shape.setAsBox(rect.width / 2, rect.height / 2)
        body.createFixture(shape, 0.5f)

        return body
    }

    private fun mapobjToRectangle(mapObject: MapObject): Rectangle {
        val rect = Rectangle(
                mapObject.properties.get("x", Float::class.java),
                mapObject.properties.get("y", Float::class.java),
                mapObject.properties.get("width", Float::class.java),
                mapObject.properties.get("height", Float::class.java)
        )

        rect.scale(scaler.xScale, scaler.yScale)
        return rect
    }

}