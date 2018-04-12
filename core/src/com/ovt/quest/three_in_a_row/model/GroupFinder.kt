package com.ovt.quest.three_in_a_row.model

import com.ovt.quest.three_in_a_row.Direction
import com.ovt.quest.three_in_a_row.Direction.*
import com.ovt.quest.three_in_a_row.model.Item.Type.Hole

/**
 * Created by nikolay on 23.03.18.
 */
object GroupFinder {

    fun findGroups(matrix: Matrix): List<List<Item>> {
        val allGroups = mutableListOf<List<Item>>()
        for (r in 0 until matrix.maxRows) {
            for (c in 0 until matrix.maxColumns) {
                val curr = matrix.get(c, r)!!

                if (wasUsed(curr, allGroups) || curr.type == Hole) continue

                try {

                    var group = searchGroupMultiDirection(
                            curr = curr,
                            requiredType = curr.type,
                            matrix = matrix,
                            mainDirection = Right
                    )

                    if (group.isEmpty()) {
                        group = searchGroupMultiDirection(
                                curr = curr,
                                requiredType = curr.type,
                                matrix = matrix,
                                mainDirection = Up
                        )
                    }


                    if (group.isNotEmpty() && group.size > 2 && hasThreeInARow(group)) {
                        allGroups.add(group)
                    }
                } catch (e: StackOverflowError) {
                    e.printStackTrace()
                    matrix.print()
                    println("at r=$r c=$c")
                }
            }
        }

        return allGroups
    }

    private fun hasThreeInARow(group: List<Item>, curr: Item = group.first(), counter: Int = 0): Boolean {
        val neighbour = group.find { it.isNeighbourTo(curr) }
        if (neighbour != null) {
            return hasThreeInARow(group - curr, neighbour, counter + 1)
        } else {
            return counter > 2
        }
    }

    private fun searchGroupMultiDirection(
            curr: Item?,
            requiredType: Item.Type,
            matrix: Matrix,
            mainDirection: Direction,
            accum: MutableList<Item> = mutableListOf()
    ): List<Item> {
        if (curr == null || curr.type != requiredType) {
            return accum
        } else {
            val forkDirections = Direction.values().filter { it != mainDirection && it != mainDirection.opposite() }
            for (forkDirection in forkDirections) {
                val next = curr.getNext(forkDirection, matrix)
                if (next?.type == requiredType) {
                    accum += searchGroupMultiDirection(
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
                    accum
            )
        }
    }

    private fun wasUsed(item: Item, allGroups: List<List<Item>>) = allGroups.flatMap { it }.contains(item)


}