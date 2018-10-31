package com.ovt.quest.archery.events

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Contact

object Bodies {

    var arrow: Body? = null
    // Костыли чтобы вызвать прилипание стрелы после world.step
    var arrowAndTargetContact: Contact? = null
    var arrowAndGroundContact: Contact? = null


}