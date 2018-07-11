package com.ovt.quest.three_in_a_row.service

import com.ovt.quest.commons.Direction
import com.ovt.quest.commons.Direction.*
import com.ovt.quest.three_in_a_row.model.AvailableTurn
import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Matrix

object Prediction {

    fun findAvailableTurns(matrix: Matrix): List<AvailableTurn> {

        var availableTurns = listOf<AvailableTurn>()

        for (i in 0 until matrix.maxRows) {
            val row = matrix.getRow(i)
            for (j in 0 until matrix.maxColumns - 2) {
                val group = listOf(row[j], row[j + 1], row[j + 2]).requireNoNulls()
                val type__count = countByColors(group)
                if (isIncompleteGroup(type__count)) {
                    val groupType = getGroupType(type__count)
                    val unmatchedItem = getUnmatchedItem(group, groupType)
                    val dirToSwap = directionToSwap(groupType, group, matrix)

                    if (dirToSwap != null) {
                        val itemToSwap = unmatchedItem.getNext(dirToSwap, matrix)!!
                        availableTurns += AvailableTurn(unmatchedItem to itemToSwap, group)
                    }
                }
            }
        }

        return availableTurns
    }

    fun countByColors(xs: List<Item>): Map<Item.Type, List<Item>> {
        val type__count = mutableMapOf<Item.Type, List<Item>>()
        xs.forEach { item ->
            val simmilarItems = type__count[item.type]
            if (simmilarItems != null) {
                 type__count[item.type] = simmilarItems + item
            } else {
                type__count[item.type] = listOf(item)
            }
        }
        return type__count
    }

    fun isIncompleteGroup(type__count: Map<Item.Type, List<Item>>): Boolean {
        val maxEntry = type__count.maxBy { it.value.size }!!
        if (maxEntry.key == Item.Type.Hole) return false
        return maxEntry.value.size > 1
    }

    fun directionToSwap(groupType: Item.Type, group: List<Item>, matrix: Matrix): Direction? {
        val item = getUnmatchedItem(group, groupType)
        val direction = getDirectionVector(group[0], group[1])
        val (dir1, dir2) = direction.normal()

        return if (item.getNext(dir1, matrix)?.type == groupType)
            dir1 else if (item.getNext(dir2, matrix)?.type == groupType)
            dir2 else
            null
    }

    fun getUnmatchedItem(group: List<Item>, groupType: Item.Type): Item {
        try {
            return group.find { it.type != groupType }!!
        } catch (e: Exception) {
            println(group)
            e.printStackTrace()
            System.exit(0)
            return Item(1, 1, Item.Type.Red)
        }
    }

    fun getGroupType(type__count: Map<Item.Type, List<Item>>): Item.Type {
        val maxEntry = type__count.maxBy { it.value.size }!!
        return maxEntry.key
    }

    fun getDirectionVector(i1: Item, i2: Item): Direction {
        return if (i1.column == i2.column) {
             if (i1.row > i2.row)
                Down else
                Up
        } else if (i1.row == i2.row) {
             if (i1.column > i2.column)
                 Left else
                 Right
        } else {
            throw RuntimeException("$i1 and $i2 are not neighbours")
        }
    }
}