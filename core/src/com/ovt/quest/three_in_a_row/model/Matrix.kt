package com.ovt.quest.three_in_a_row.model

/**
 * Created by nikolay on 15.03.18.
 */
internal class Matrix(private val width: Int, private val height: Int) {
    private var matrix: MutableList<MutableList<Item?>> = MutableList(height, {
        MutableList<Item?>(width, { null })
    })

    fun put(item: Item, column: Int, row: Int) {
        matrix[column][row] = item
    }

    fun get(column: Int, row: Int): Item? {
        try {
            return matrix[column][row]
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }

    fun set(item: Item, column: Int, row: Int) {
        try {
            matrix[column][row] = item
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }

    fun row(rownum: Int): List<Item?> = matrix[rownum]

    fun remove(column: Int, row: Int) {
        matrix[column][row] = null
    }

    fun swap(col1: Int, row1: Int, col2: Int, row2: Int) {
        val x = matrix[col1][row1]
        matrix[col1][row1] = matrix[col2][row2]
        matrix[col2][row2] = x
    }

}