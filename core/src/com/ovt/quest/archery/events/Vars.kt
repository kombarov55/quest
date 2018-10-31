package com.ovt.quest.archery.events

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Contact
import com.ovt.quest.archery.pl.CameraMovementType
import com.ovt.quest.archery.pl.CameraMovementType.FREE

object Vars {

    var arrow: Body? = null
    // Костыли чтобы вызвать прилипание стрелы после world.step
    var arrowAndTargetContact: Contact? = null
    var arrowAndGroundContact: Contact? = null
    var cameraMovementType: CameraMovementType = FREE


}