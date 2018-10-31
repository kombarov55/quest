package com.ovt.quest.archery.events.dto

import com.badlogic.gdx.math.Vector2

data class BowFiredDto(
        val firingPosition: Vector2,
        val rotation: Float,
        val power: Float
)