package com.ovt.quest.three_in_a_row.service

import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Item.Type.Hole
import com.ovt.quest.three_in_a_row.model.Matrix

/**
 * Created by nikolay on 23.03.18.
 */
object GroupFinder {

    val stack = mutableListOf<Item?>()

    /**
     * Направление от центра -> количество
     */
    val figures = listOf(
            // плюсом
            listOf(0 to 1, 0 to 2, 0 to 3, 0 to 4, -1 to 2, -2 to 2, 1 to 2, 2 to 2),
            listOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 2 to 1, 2 to 2),
            //углы
            listOf(1 to 0, 2 to 0, 0 to 1, 0 to 2),
            listOf(0 to 1, 0 to 2, 1 to 2, 2 to 2),
            listOf(1 to 0, 2 to 0, 2 to 1, 2 to 2),
            listOf(0 to 1, 0 to 2, -1 to 2, -2 to 2),
            // горизонтальные в ряд
            listOf(1 to 0, 2 to 0, 3 to 0, 4 to 0),
            listOf(1 to 0, 2 to 0, 3 to 0),
            listOf(1 to 0, 2 to 0),
            //Вертикальные в ряд
            listOf(0 to 1, 0 to 2, 0 to 3, 0 to 4),
            listOf(0 to 1, 0 to 2, 0 to 3),
            listOf(0 to 1, 0 to 2)
    )

    fun findGroups(matrix: Matrix): List<List<Item>> {
        val allGroups = mutableListOf<List<Item>>()
        for (r in 0 until matrix.maxRows) {
            for (c in 0 until matrix.maxColumns) {
                val curr = matrix.get(c, r)!!
                if (wasUsed(curr, allGroups) || curr.type == Hole) continue

                figures.any { figure ->
                    val group = search(curr, matrix, figure)
                    val matched = group.size == figure.size + 1
                    if (matched) {
                        allGroups.add(group)
                    }

                    matched
                }

            }
        }

        return allGroups
    }

    private fun search(curr: Item, matrix: Matrix, figure: List<Pair<Int, Int>>): List<Item>  {
        val group = mutableListOf<Item?>(curr)
        for (point in figure) {
            val (columnOffset, rowOffset) = point
            group += curr.getByOffset(columnOffset, rowOffset, matrix)
        }

        return group
                .mapNotNull { it }
                .filter { it.type == curr.type }


    }

    private fun wasUsed(item: Item, allGroups: List<List<Item>>) = allGroups.flatMap { it }.contains(item)


}