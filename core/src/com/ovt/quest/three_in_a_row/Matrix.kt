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


    fun get(column: Int, row: Int): T? = matrix[row][column]

}