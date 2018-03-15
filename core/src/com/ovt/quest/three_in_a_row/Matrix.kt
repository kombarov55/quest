package com.ovt.quest.three_in_a_row

/**
 * Created by nikolay on 15.03.18.
 */
class Matrix<T>(width: Int, height: Int) {
    private var matrix: MutableList<MutableList<T?>> = MutableList(height, {
        MutableList<T?>(width, { null })
    })


    fun add(item: T, column: Int, row: Int) {
        matrix[row][column] = item
    }


    fun get(column: Int, row: Int): T? {
        try {
            return matrix[row][column]
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }

    fun swap(col1: Int, row1: Int, col2: Int, row2: Int) {
        val x = matrix[row1][col1]
        matrix[row1][col1] = matrix[row2][col2]
        matrix[row2][col2] = x
    }

}