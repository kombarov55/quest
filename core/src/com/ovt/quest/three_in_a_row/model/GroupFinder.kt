package com.ovt.quest.three_in_a_row.model

import com.ovt.quest.three_in_a_row.model.GroupFinder.State.*
import com.ovt.quest.three_in_a_row.model.Item.Type.Hole

/**
 * Created by nikolay on 23.03.18.
 */
object GroupFinder {

    private enum class State { DirectionSearch, Up, Right, GoNext }

    private var state: State = DirectionSearch

    var allGroups = mutableListOf<MutableList<Item>>()
    var currentGroup = mutableListOf<Item>()

    fun findGroups(matrix: Matrix): List<List<Item>> {
        for (r in 0 until matrix.maxRows) {
            for (c in 0 until matrix.maxColumns) {
                val curr = matrix.get(c, r)!!
                do {
                    when (state) {
                        DirectionSearch -> directionSearch(curr, matrix)
                        Up -> up(curr, matrix)
                        Right -> right(curr, matrix)
                    }
                } while (state != DirectionSearch)
            }
        }

        val result = allGroups
        allGroups = mutableListOf()

        return result
    }

    fun directionSearch(curr: Item, matrix: Matrix) {
        val up = curr.upOfSelf(matrix)
        val right = curr.rightOfSelf(matrix)

        if (right?.type == curr.type && curr.type != Hole) {
            currentGroup = mutableListOf(curr, right)

            state = Right
        } else if (up?.type == curr.type && curr.type != Hole) {
            currentGroup = mutableListOf(curr, up)

            state = Up

        }


    }

    private fun right(curr: Item, matrix: Matrix) {
        val next = currentGroup.last().rightOfSelf(matrix)
        if (next?.type == curr.type) {
            currentGroup.add(next)
        } else {
            if (currentGroup.size >= 3) allGroups.add(currentGroup)

            state = DirectionSearch
        }
    }

    private fun up(curr: Item, matrix: Matrix) {
        val next = currentGroup.last().upOfSelf(matrix)
        if (next?.type == curr.type) {
            currentGroup.add(next)
        } else {
            if (currentGroup.size >= 3) allGroups.add(currentGroup)

            state = DirectionSearch
        }
    }

}