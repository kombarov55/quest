package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.ovt.quest.archery.box2d.Collision
import com.ovt.quest.archery.box2d.getFixtureUserData
import com.ovt.quest.commons.MyAnimation
import kotlin.experimental.or

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

    fun createTargetCollisionLine(): Body {
        val bdef = BodyDef()
        bdef.type = BodyDef.BodyType.StaticBody
        val body = world.createBody(bdef)

        val vertices = tilemapHelper.getTargetCollisionLine()
        val fdef = verticesArrayToFdef(vertices)
        fdef.filter.categoryBits = Collision.TARGET_BITS
        fdef.filter.maskBits = Collision.ARROW_BITS

        body.createFixture(fdef)
        body.getFixtureUserData()["type"] = Collision.TARGET_TYPE

        return body
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

        val vertices = tilemapHelper.getArrowVertices()
//        val vertices = arrayOf(Vector2(-1.4f, 0f), Vector2(0f, -0.1f), Vector2(0.6f, 0f), Vector2(0f, 0.1f))
//        var vertices = arrayOf(Vector2(-1.4f * 5, 0f), Vector2(0f, -0.1f * 5), Vector2(0.6f * 5, 0f), Vector2(0f, 0.1f * 5))

        val shape = PolygonShape()
        shape.set(vertices)
        //TODO: поменять density
        val fdef = FixtureDef()
        fdef.shape = shape
        fdef.density = 1f
        fdef.friction = 1f
        fdef.restitution = 0.5f

        fdef.filter.categoryBits = Collision.ARROW_BITS
        fdef.filter.maskBits = Collision.TARGET_BITS or Collision.GROUND_BITS

        body.createFixture(fdef)
        body.setTransform(body.worldCenter, MathUtils.degreesToRadians * degrees)

        body.angularDamping = 3f
        body.getFixtureUserData()["type"] = Collision.ARROW_TYPE

        return body
    }

    fun createGround(): Body {
        val vertices = tilemapHelper.getGroundVertices()

        val bdef = BodyDef()
        bdef.type = BodyDef.BodyType.StaticBody
        val body = world.createBody(bdef)

        val shape = PolygonShape()
        shape.set(vertices)

        val fdef = FixtureDef()
        fdef.shape = shape
        fdef.density = 1f
        fdef.friction = 1f
        fdef.restitution = 0.5f
        fdef.filter.categoryBits = Collision.GROUND_BITS
        fdef.filter.maskBits = Collision.ARROW_BITS

        body.createFixture(fdef)

        body.getFixtureUserData()["type"] = Collision.GROUND_TYPE

        return body
    }

    private fun verticesArrayToFdef(vertices: FloatArray): FixtureDef {
        val shape = PolygonShape()
        shape.set(vertices)

        val fdef = FixtureDef()
        fdef.shape = shape

        return fdef
    }

}