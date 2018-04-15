package com.ovt.quest.three_in_a_row.model
import com.badlogic.gdx.scenes.scene2d.Actor
import com.ovt.quest.three_in_a_row.Direction
import com.ovt.quest.three_in_a_row.Direction.*
import com.ovt.quest.three_in_a_row.toPositive

/**
 * Created by nikolay on 15.03.18.
 */
open class Item internal constructor (
        var column: Int,
        var row: Int,
        val type: Type,
        val itemActor: ItemActor? = null
) : Actor() {



    enum class Type {
        Red, Blue, Yellow, Pink, Hole
    }

    fun leftOfSelf(matrix: Matrix): Item? = matrix.get(column - 1, row)
    fun upOfSelf(matrix: Matrix): Item? = matrix.get(column, row + 1)
    fun rightOfSelf(matrix: Matrix): Item? = matrix.get(column + 1, row)
    fun downOfSelf(matrix: Matrix): Item? = matrix.get(column, row - 1)

    fun getNext(direction: Direction, matrix: Matrix): Item? = when(direction) {
        Up -> upOfSelf(matrix)
        Right -> rightOfSelf(matrix)
        Down -> downOfSelf(matrix)
        Left -> leftOfSelf(matrix)
    }

    fun isNeighbourTo(i: Item): Boolean  {
        return (column == i.column && toPositive(row - i.row) == 1) ||
                (row == i.row && toPositive(column - i.column) == 1)
    }

    fun setLogicCoords(column: Int, row: Int) {
        this.column = column
        this.row = row
    }

    override fun toString(): String {
        return "{$column $row}"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Item) {
            return other.column == column && other.row == row
        } else
            return false
    }

    companion object {
        fun swapLogicCoords(i1: Item, i2: Item) {
            val (i1c, i1y) = i1.column to i1.row
            i1.setLogicCoords(i2.column, i2.row)
            i2.setLogicCoords(i1c, i1y)
        }
    }

    fun getByOffset(columnOffset: Int, rowOffset: Int, matrix: Matrix): Item? {
        return matrix.get(column + columnOffset, row + rowOffset)
    }
}