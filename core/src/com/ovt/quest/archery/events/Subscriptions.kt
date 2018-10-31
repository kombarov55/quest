package com.ovt.quest.archery.events

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Contact
import com.ovt.quest.archery.box2d.Collision.ARROW_TYPE
import com.ovt.quest.archery.box2d.getUserDataMap
import com.ovt.quest.archery.events.dto.BowFiredDto
import com.ovt.quest.archery.objects.ObjectFactory
import com.ovt.quest.archery.pl.CameraMovementType.FOLLOWING_ARROW
import com.ovt.quest.archery.pl.CameraMovementType.FREE

class Subscriptions(private val objectFactory: ObjectFactory) {

    fun makeSubscriptions() {
        Events.touchDown.subscribe { releaseCamera() }
        Events.bowFired.subscribe { onBowFired(it) }
        Events.bowFired.subscribe { followArrow() }
        Events.targetHit.subscribe { attachArrow(it.contact) }
        Events.groundHit.subscribe { attachArrow(it.contact) }
    }

    private fun onBowFired(bowFiredDto: BowFiredDto) {
        val arrow = objectFactory.createArrow(bowFiredDto.firingPosition, bowFiredDto.rotation)
        Vars.arrow = arrow

        val xForce = MathUtils.cos(arrow.angle) * bowFiredDto.power
        val yForce = MathUtils.sin(arrow.angle) * bowFiredDto.power
        arrow.setLinearVelocity(xForce, yForce)
    }

    private fun attachArrow(contact: Contact) {

        val type1 = contact.fixtureA.getUserDataMap()["type"]

        val arrowFixture =
                if (type1 == ARROW_TYPE)
                    contact.fixtureA else
                    contact.fixtureB

        arrowFixture.body.type = BodyDef.BodyType.StaticBody
    }

    private fun followArrow() {
        Vars.cameraMovementType = FOLLOWING_ARROW
    }

    private fun releaseCamera() {
        if (Vars.cameraMovementType == FOLLOWING_ARROW) {
            Vars.cameraMovementType = FREE
        }
    }

}