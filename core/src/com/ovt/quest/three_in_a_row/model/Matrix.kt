package com.ovt.quest.three_in_a_row.model

/**
 * Created by nikolay on 15.03.18.
 */
class Matrix(private val width: Int, private val height: Int) {
    private var matrix: MutableList<MutableList<Item?>> = MutableList(height, {
        MutableList<Item?>(width, { null })
    })

    fun put(item: Item, column: Int, row: Int) {
        matrix[column][row] = item
    }

    fun put(item: Item) {
        matrix[item.column][item.row] = item
    }

    fun get(column: Int, row: Int): Item? {
        try {
            return matrix[column][row]
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }

    fun remove(column: Int, row: Int) {
        matrix[column][row] = null
    }

    fun forEach(f: (Item?) -> Unit) {
        matrix.flatten().forEach(f)
    }

}