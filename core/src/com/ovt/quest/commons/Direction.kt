package com.ovt.quest.commons

/**
 * Created by nikolay on 23.03.18.
 */
enum class Direction {
    Up, Right, Down, Left;

    fun opposite(): Direction {
        val destOrdinal = (this.ordinal + 2) % 4
        return Direction.values()[destOrdinal]
    }
}