package com.ovt.quest.three_in_a_row.model
import com.badlogic.gdx.scenes.scene2d.Actor
import com.ovt.quest.three_in_a_row.Direction
import com.ovt.quest.three_in_a_row.Direction.*

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
}