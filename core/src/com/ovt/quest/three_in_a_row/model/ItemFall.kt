package com.ovt.quest.three_in_a_row.model

import com.ovt.quest.three_in_a_row.model.ItemFall.State.*
import com.ovt.quest.three_in_a_row.model.Item.Type.*

/**
 * Created by nikolay on 24.03.18.
 */
object ItemFall {

    private enum class State { SearchingHole, CountHolesInARow, FallingItems }

    private var state: State = SearchingHole

    private var holeCount = 0

    fun executeFallDown(matrix: Matrix, items: Items) {
        for (column in 0 until matrix.maxColumns) {
            for (row in 0 until matrix.maxRows) {
                val item = matrix.get(column, row)!!
                when (state) {
                    SearchingHole -> searchingHole(item, matrix, items)
                    CountHolesInARow -> countHolesInARow(item, matrix, items)
                    FallingItems -> fallingItems(item, matrix, items)
                }
            }

            holeCount = 0
            state = SearchingHole
        }
    }

    private fun searchingHole(item: Item, matrix: Matrix, items: Items) {
        if (item.type == Hole) {
            state = CountHolesInARow

            holeCount += 1
        }
    }

    private fun countHolesInARow(item: Item, matrix: Matrix, items: Items) {
        if (item.type == Hole) {
            holeCount += 1
        } else {
            state = FallingItems
            fallingItems(item, matrix, items)
        }
    }

    private fun fallingItems(item: Item, matrix: Matrix, items: Items) {
        matrix.put(items.hole(item.column, item.row))
        item.moveTo(item.column, item.row - holeCount)
    }
}