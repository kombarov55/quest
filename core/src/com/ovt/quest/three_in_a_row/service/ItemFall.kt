package com.ovt.quest.three_in_a_row.service

import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Item.Type.Hole
import com.ovt.quest.three_in_a_row.model.ItemFactory
import com.ovt.quest.three_in_a_row.model.RenderingMatrix

/**
 * Created by nikolay on 24.03.18.
 */
class ItemFall(
        private val matrix: RenderingMatrix,
        private val itemFactory: ItemFactory
) {

    private enum class State { SearchingHole, CountHolesInARow, FallingItems }

    private var state: State = State.SearchingHole

    private var holeCount = 0

    private var thenCalled = false

    fun executeFallDown(matrix: RenderingMatrix, itemFactory: ItemFactory, then: () -> Unit = { println("after fall down!") }) {
        thenCalled = false

        for (column in 0 until matrix.maxColumns) {
            for (row in 0 until matrix.maxRows) {
                val item = matrix.get(column, row)!!
                when (state) {
                    State.SearchingHole -> searchingHole(item)
                    State.CountHolesInARow -> countHolesInARow(item, then)
                    State.FallingItems -> fallingItems(item, then)
                }
            }

            holeCount = 0
            state = State.SearchingHole
        }

        if (!thenCalled) then.invoke()
    }

    private fun searchingHole(item: Item) {
        if (item.type == Hole) {
            state = State.CountHolesInARow

            holeCount = 1
        }
    }

    private fun countHolesInARow(itemActor: Item, then: () -> Unit) {
        if (itemActor.type == Hole) {
            holeCount += 1
        } else {
            state = State.FallingItems
            fallingItems(itemActor, then)
        }
    }

    private fun fallingItems(item: Item, then: () -> Unit) {
        if (item.type != Hole) {
            matrix.put(itemFactory.hole(item.column, item.row))
            matrix.put(item, item.column, item.row - holeCount)




            if (!thenCalled) {
                item.itemActor?.slowMoveTo(matrix.project(item.column, item.row - holeCount), then)
                item.setLogicCoords(item.column, item.row - holeCount)
                thenCalled = true
            } else {
                item.itemActor?.slowMoveTo(matrix.project(item.column, item.row - holeCount))
                item.setLogicCoords(item.column, item.row - holeCount)
            }
        } else {
            state = State.CountHolesInARow
            holeCount += 1
        }
    }
}