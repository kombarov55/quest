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

    fun normal(): List<Direction> {
        val destOrdinal1 = (this.ordinal + 1) % 4
        val destOrdinal2 = (destOrdinal1 + 2) % 4
        return listOf(Direction.values()[destOrdinal1], Direction.values()[destOrdinal2])
    }
}