package com.ovt.quest.archery.box2d

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.ovt.quest.archery.box2d.Collision.ARROW_TYPE
import com.ovt.quest.archery.box2d.Collision.TARGET_TYPE
import com.ovt.quest.archery.events.Bodies

class ArcheryContactListener : ContactListener {

    override fun beginContact(contact: Contact) {
        val type1 = contact.fixtureA.getUserDataMap()["type"]
        val type2 = contact.fixtureB.getUserDataMap()["type"]

        val arrowAndTarget = (type1 == ARROW_TYPE && type2 == TARGET_TYPE) || (type1 == TARGET_TYPE && type2 == ARROW_TYPE)

        if (arrowAndTarget) {
            Bodies.contact = contact
        }

    }

    override fun endContact(contact: Contact) {

    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}

    override fun postSolve(contact: Contact, impulse: ContactImpulse?) {
    }
}