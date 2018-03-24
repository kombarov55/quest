package com.ovt.quest.three_in_a_row.model

import com.ovt.quest.three_in_a_row.model.MatchResolver.State.*

/**
 * Created by nikolay on 23.03.18.
 */
object MatchResolver {

    private enum class State { DirectionSearch, Up, Right, GoNext }

    private var state: State = DirectionSearch

    var allMatches = mutableListOf<MutableList<Item>>()
    var currentMatch = mutableListOf<Item>()

    fun resolveMatches(matrix: Matrix): List<List<Item>> {
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

        val result = allMatches
        allMatches = mutableListOf()

        return result
    }

    fun directionSearch(curr: Item, matrix: Matrix) {
        val up = curr.upOfSelf(matrix)
        val right = curr.rightOfSelf(matrix)

        if (right?.type == curr.type) {
            currentMatch = mutableListOf(curr, right)

            state = Right
        } else if (up?.type == curr.type) {
            currentMatch = mutableListOf(curr, up)

            state = Up

        }


    }

    private fun right(curr: Item, matrix: Matrix) {
        val next = currentMatch.last().rightOfSelf(matrix)
        if (next?.type == curr.type) {
            currentMatch.add(next)
        } else {
            if (currentMatch.size >= 3) allMatches.add(currentMatch)

            state = DirectionSearch
        }
    }

    private fun up(curr: Item, matrix: Matrix) {
        val next = currentMatch.last().upOfSelf(matrix)
        if (next?.type == curr.type) {
            currentMatch.add(next)
        } else {
            if (currentMatch.size >= 3) allMatches.add(currentMatch)

            state = DirectionSearch
        }
    }

}