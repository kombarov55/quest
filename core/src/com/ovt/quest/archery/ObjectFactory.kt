package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.PolygonMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.ovt.quest.commons.MyAnimation
import com.ovt.quest.commons.scale

class ObjectFactory(private val world: World,
                    private val tilemap: TiledMap,
                    private val scaler: Scaler) {




    fun createBow(): Bow {
        val mapobj = getObject("bow")
        val r = mapobjToRectangle(mapobj)
        //TODO: сделать загрузчик текстур
        val t = Texture(Gdx.files.internal("maps/archery/bow-anim.png"))
        val animation = MyAnimation(t, 70, 90)
        return Bow(animation, r)
    }

    fun createTarget(): Target {
        val mapobj = getObject("target")
        val r = mapobjToRectangle(mapobj)
        val t = Texture(Gdx.files.internal("maps/archery/target-rescaled.png"))
        return Target(t, r)
    }

    fun getZone(): Rectangle {
        val mapobj = getArea("rotation-area")
        return mapobjToRectangle(mapobj)
    }

    private fun rectangleToBody(rect: Rectangle): Body {
        val bdef = BodyDef()
        bdef.position.set(rect.x + rect.width/2, rect.y + rect.height/2)
        val body = world.createBody(bdef)

        val shape = PolygonShape()
        shape.setAsBox(rect.width / 2, rect.height / 2)
        body.createFixture(shape, 1f)

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

    fun createArrow(center: Vector2, degrees: Float) {
        val bdef = BodyDef()
        bdef.position.set(center)
        //TODO: потом сделать динамическим
        bdef.type = BodyDef.BodyType.StaticBody
        val body = world.createBody(bdef)

        val mapobj = getObject("arrow") as PolygonMapObject
        val vertices = mapobj.polygon.vertices
        val scaledVertices = scaler.toWorldCoords(vertices)

        val shape = PolygonShape()
        shape.set(scaledVertices)
        //TODO: поменять density
        body.createFixture(shape, 1f)
        val radians = MathUtils.degreesToRadians * degrees
        body.setTransform(body.worldCenter, radians)
    }

    private fun getObject(name: String): MapObject = tilemap.layers["objects"].objects[name]

    private fun getArea(name: String): MapObject = tilemap.layers["areas"].objects[name]

}