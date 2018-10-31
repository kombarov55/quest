package com.ovt.quest.archery.events

import com.badlogic.gdx.math.MathUtils
import com.ovt.quest.archery.Bodies
import com.ovt.quest.archery.ObjectFactory
import com.ovt.quest.archery.events.dto.BowFiredDto

class Subscriptions(private val objectFactory: ObjectFactory) {

    fun makeSubscriptions() {
        Events.bowFired.subscribe(this::onBowFired)
    }

    private fun onBowFired(bowFiredDto: BowFiredDto) {
        val arrow = objectFactory.createArrow(bowFiredDto.firingPosition, bowFiredDto.rotation)
        Bodies.arrow = arrow

        val xForce = MathUtils.cos(arrow.angle) * bowFiredDto.power
        val yForce = MathUtils.sin(arrow.angle) * bowFiredDto.power
        arrow.setLinearVelocity(xForce, yForce)
    }

}