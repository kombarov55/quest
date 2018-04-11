package com.ovt.quest.three_in_a_row.model

import com.ovt.quest.three_in_a_row.model.Item.Type.Hole
import com.ovt.quest.three_in_a_row.model.ItemFall.State.*

/**
 * Created by nikolay on 24.03.18.
 */
class ItemFall(
        private val matrix: Matrix,
        private val itemFactory: ItemFactory
) {

    private enum class State { SearchingHole, CountHolesInARow, FallingItems }

    private var state: State = SearchingHole

    private var holeCount = 0

    private var thenCalled = false

    fun executeFallDown(matrix: Matrix, itemFactory: ItemFactory, then: () -> Unit = { println("after fall down!") }) {
        thenCalled = false

        for (column in 0 until matrix.maxColumns) {
            for (row in 0 until matrix.maxRows) {
                val item = matrix.get(column, row)!!
                when (state) {
                    SearchingHole -> searchingHole(item)
                    CountHolesInARow -> countHolesInARow(item, then)
                    FallingItems -> fallingItems(item, then)
                }
            }

            holeCount = 0
            state = SearchingHole
        }

        if (!thenCalled) then.invoke()
    }

    private fun searchingHole(item: Item) {
        if (item.type == Hole) {
            state = CountHolesInARow

            holeCount = 1
        }
    }

    private fun countHolesInARow(item: Item, then: () -> Unit) {
        if (item.type == Hole) {
            holeCount += 1
        } else {
            state = FallingItems
            fallingItems(item, then)
        }
    }

    private fun fallingItems(item: Item, then: () -> Unit) {
        if (item.type != Hole) {
            matrix.put(itemFactory.hole(item.column, item.row))
            matrix.put(item, item.column, item.row - holeCount)

            val destRow = item.row - holeCount
            if (!thenCalled) {
                item.slowMoveTo(matrix.translate(item.column, destRow), then)
                thenCalled = true
            } else {
                item.slowMoveTo(matrix.translate(item.column, destRow))
            }
        } else {
            state = CountHolesInARow
            holeCount += 1
        }
    }
}