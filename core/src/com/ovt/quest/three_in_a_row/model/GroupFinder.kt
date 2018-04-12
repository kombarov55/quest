package com.ovt.quest.three_in_a_row.model

import com.ovt.quest.three_in_a_row.Direction
import com.ovt.quest.three_in_a_row.Direction.*
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

                var groups = searchGroupMultiDirection(
                        curr = curr,
                        requiredType = curr.type,
                        matrix = matrix,
                        mainDirection = Right,
                        forkDirections = listOf(Up, Down)
                )

                if (groups.isEmpty()) {
                    groups = searchGroupMultiDirection(
                            curr = curr,
                            requiredType = curr.type,
                            matrix = matrix,
                            mainDirection = Up,
                            forkDirections = listOf(Left, Right)
                    )
                }

                if (groups.isNotEmpty() && groups.size > 2) {
                    allGroups.add(groups)
                }
            }
        }

        val result = allGroups
        allGroups = mutableListOf()


        return result
    }

    private fun searchGroupMultiDirection(
            curr: Item?,
            requiredType: Item.Type,
            matrix: Matrix,
            mainDirection: Direction,
            forkDirections: List<Direction>,
            accum: MutableList<Item> = mutableListOf()
    ): List<Item> {
        if (curr == null || curr.type != requiredType) {
            return accum
        } else {
            for (forkDirection in forkDirections) {
                val next = curr.getNext(forkDirection, matrix)
                if (next?.type == requiredType) {
                    accum += searchGroupSingleDirection(
                            next,
                            requiredType,
                            matrix,
                            forkDirection
                    )
                }
            }
            accum += curr
            return searchGroupMultiDirection(
                    curr.getNext(mainDirection, matrix),
                    requiredType,
                    matrix,
                    mainDirection,
                    forkDirections,
                    accum
            )
        }
    }

    private fun searchGroupSingleDirection(
            curr: Item?,
            requiredType: Item.Type,
            matrix: Matrix,
            direction: Direction,
            accum: List<Item> = emptyList()
    ): List<Item> {
        if (curr == null || curr.type != requiredType) {
            return accum
        } else {
            return searchGroupSingleDirection(
                    curr.getNext(direction, matrix),
                    requiredType,
                    matrix,
                    direction,
                    (accum + curr)
            )
        }

    }

    private fun searchGroup(type: Item.Type, item: Item?, matrix: Matrix, direction: Direction, accum: List<Item> = emptyList()): List<Item> {
        if (item == null || item.type != type) {
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

            val next = item.getNext(direction, matrix)

            return searchGroup(type, next, matrix, direction, accum + item)
        }
    }

    private fun wasUsed(item: Item) = allGroups.flatMap { it }.contains(item)


}