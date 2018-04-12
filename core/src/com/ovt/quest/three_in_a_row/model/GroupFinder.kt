package com.ovt.quest.three_in_a_row.model

import com.ovt.quest.three_in_a_row.Direction
import com.ovt.quest.three_in_a_row.Direction.Right
import com.ovt.quest.three_in_a_row.Direction.Up
import com.ovt.quest.three_in_a_row.model.Item.Type.Hole

/**
 * Created by nikolay on 23.03.18.
 */
object GroupFinder {


    private var allGroups = mutableListOf<List<Item>>()

    private data class Fork(val next: Item, val direction: Direction)
    private var fork: Fork? = null

    fun findGroups(matrix: Matrix): List<List<Item>> {
        for (r in 0 until matrix.maxRows) {
            for (c in 0 until matrix.maxColumns) {
                val curr = matrix.get(c, r)!!

                if (wasUsed(curr) || curr.type == Hole) continue

                var group = searchGroup(curr, matrix, Right, listOf(curr))
                if (group.isEmpty()) {
                    group = searchGroup(curr, matrix, Up)
                }

                if (group.isNotEmpty() && fork != null) {
                    group += searchGroup(fork!!.next, matrix, fork!!.direction, emptyList())
                }

                if (group.size > 2) {
                    allGroups.add(group)
                    fork = null
                }
            }
        }

        val result = allGroups
        allGroups = mutableListOf()


        return result
    }

    private fun searchGroup(item: Item, matrix: Matrix, direction: Direction, accum: List<Item> = emptyList()): List<Item> {
        val next = item.getNext(direction, matrix)

        if (next == null || next.type != item.type) {
            return accum
        } else {
            if (fork != null) {
                val forkDirection = if (direction == Right) Up else Right

                val forkItem = item.getNext(
                        forkDirection,
                        matrix
                )

                if (forkItem?.type == item.type) {
                    fork = Fork(forkItem, forkDirection)
                }
            }

            return searchGroup(next, matrix, direction, accum + next)
        }
    }

    private fun wasUsed(item: Item) = allGroups.flatMap { it }.contains(item)


}