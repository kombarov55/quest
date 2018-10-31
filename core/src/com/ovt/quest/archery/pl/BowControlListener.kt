package com.ovt.quest.archery.pl

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.ovt.quest.archery.events.Bodies
import com.ovt.quest.archery.events.Events
import com.ovt.quest.archery.events.dto.BowFiredDto
import com.ovt.quest.archery.objects.Bow
import com.ovt.quest.archery.objects.ObjectFactory

class BowControlListener(private val zone: Rectangle, private val scaler: Scaler, private val bow: Bow, private val objectFactory: ObjectFactory): InputAdapter() {

    private var isDragging = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val p = scaler.toWorldCoords(screenX, screenY)
        if (zone.contains(p)) {
            isDragging = true
            return true
        } else {
            return false
        }
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        if (isDragging) {
            val p = scaler.toWorldCoords(screenX, screenY)
            val angle = getAngle(p, bow)
            val distance = getDistance(p, bow)

            bow.rotation = angle
            bow.setPower(distance)
            return true
        } else {
            return false
        }
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (isDragging) {
            isDragging = false
            Events.bowFired.onNext(BowFiredDto(bow.center, bow.rotation, 50f))
            return true
        } else {
            return false
        }
    }

    //TODO: вынести в другое место
    private fun fireBow() {
        val arrow = objectFactory.createArrow(bow.center, bow.rotation)
        Bodies.arrow = arrow

        val force = 50f
        val xForce = MathUtils.cos(arrow.angle) * force
        val yForce = MathUtils.sin(arrow.angle) * force
        arrow.setLinearVelocity(xForce, yForce)
    }

    private fun getDistance(touch: Vector2, bow: Bow): Float {
        val projectedTouch = scaler.toWorldCoords(touch)
        val projectedCenter = scaler.toWorldCoords(bow.center)

        val vector = Vector2(projectedTouch.x - projectedCenter.x, projectedTouch.y - projectedCenter.y)
        return vector.len()
    }

    private fun getAngle(touch: Vector2, bow: Bow): Float {
        return MathUtils.atan2(touch.y - bow.center.y, touch.x - bow.center.x) * 180f / Math.PI.toFloat()
    }
}