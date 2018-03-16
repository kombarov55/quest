package com.ovt.quest.three_in_a_row

import com.ovt.quest.three_in_a_row.layout.Item

/**
 * Created by nikolay on 15.03.18.
 */
class Matrix(width: Int, height: Int) {
    private var matrix: MutableList<MutableList<Item?>> = MutableList(height, {
        MutableList<Item?>(width, { null })
    })


    fun add(item: Item) {
        matrix[item.row][item.column] = item
    }


    fun get(column: Int, row: Int): Item? {
        try {
            return matrix[row][column]
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }

    fun below(i: Item) = get(i.column, i.row - 1)
    fun leftOf(i: Item) = get(i.column - 1, i.row)
    fun rightOf(i: Item) = get(i.column + 1, i.row)
    fun upper(i: Item) = get(i.column, i.row + 1)


    fun remove(column: Int, row: Int) {
        matrix[row][column] = null
    }

    fun swap(col1: Int, row1: Int, col2: Int, row2: Int) {
        val x = matrix[row1][col1]
        matrix[row1][col1] = matrix[row2][col2]
        matrix[row2][col2] = x
    }

}