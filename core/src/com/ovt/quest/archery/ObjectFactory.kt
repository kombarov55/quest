package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.ovt.quest.commons.MyAnimation

//TODO: переименовать на связанное с tilemap, или же разделить функциональность на доставание из tilemap и создание объекта
class ObjectFactory(private val world: World,
                    private val tilemapHelper: TilemapHelper) {


    fun createBow(): Bow {
        val r = tilemapHelper.getBowRectangle()
        //TODO: сделать загрузчик текстур
        val t = Texture(Gdx.files.internal("maps/archery/bow-anim.png"))
        val animation = MyAnimation(t, 70, 90)
        return Bow(animation, r)
    }

    fun createTarget(): Target {
        val r = tilemapHelper.getTargetRectangle()
        val t = Texture(Gdx.files.internal("maps/archery/target-rescaled.png"))
        return Target(t, r)
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

    fun createArrow(center: Vector2, degrees: Float): Body {
        val bdef = BodyDef()
        bdef.position.set(center)
        //TODO: потом сделать динамическим
        bdef.type = BodyDef.BodyType.DynamicBody
        val body = world.createBody(bdef)

//        val mapobj = getObject("arrow") as PolygonMapObject
//        val vertices = mapobj.polygon.vertices
//        val scaledVertices = scaler.toWorldCoords(vertices)

        var vertices = arrayOf(Vector2(-1.4f, 0f), Vector2(0f, -0.1f), Vector2(0.6f, 0f), Vector2(0f, 0.1f))
//        var vertices = arrayOf(Vector2(-1.4f * 5, 0f), Vector2(0f, -0.1f * 5), Vector2(0.6f * 5, 0f), Vector2(0f, 0.1f * 5))

        val shape = PolygonShape()
        shape.set(vertices)
        //TODO: поменять density
        val fdef = FixtureDef()
        fdef.shape = shape
        fdef.density = 1f
        fdef.friction = 0.5f
        fdef.restitution = 0.5f

        body.createFixture(fdef)
        body.setTransform(body.worldCenter, MathUtils.degreesToRadians * degrees)

        val force = 20f
        val xForce = MathUtils.cos(body.angle) * force
        val yForce = MathUtils.sin(body.angle) * force
        body.setLinearVelocity(xForce, yForce)

        return body
    }

}