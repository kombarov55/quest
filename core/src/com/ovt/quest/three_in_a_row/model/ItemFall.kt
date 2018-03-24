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

    fun executeFallDown(matrix: Matrix, itemFactory: ItemFactory, then: () -> Unit = {  }) {
        for (column in 0 until matrix.maxColumns) {
            for (row in 0 until matrix.maxRows) {
                val item = matrix.get(column, row)!!
                when (state) {
                    SearchingHole -> searchingHole(item, matrix, itemFactory)
                    CountHolesInARow -> countHolesInARow(item, matrix, itemFactory)
                    FallingItems -> fallingItems(item, matrix, itemFactory)
                }
            }

            holeCount = 0
            state = SearchingHole
        }

        Thread({
            Thread.sleep((Item.fallDuration * 1000).toLong())
            then.invoke()
        }).start()
    }

    private fun searchingHole(item: Item, matrix: Matrix, itemFactory: ItemFactory) {
        if (item.type == Hole) {
            state = CountHolesInARow

            holeCount = 1
        }
    }

    private fun countHolesInARow(item: Item, matrix: Matrix, itemFactory: ItemFactory) {
        if (item.type == Hole) {
            holeCount += 1
        } else {
            state = FallingItems
            fallingItems(item, matrix, itemFactory)
        }
    }

    private fun fallingItems(item: Item, matrix: Matrix, itemFactory: ItemFactory) {
        if (item.type != Hole) {
            matrix.put(itemFactory.hole(item.column, item.row))
            matrix.put(item, item.column, item.row - holeCount)
            item.slowMoveTo(item.column, item.row - holeCount)
        } else {
            state = CountHolesInARow
            holeCount += 1
        }
    }
}