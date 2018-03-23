package com.ovt.quest.three_in_a_row.model

/**
 * Created by nikolay on 15.03.18.
 */
class Matrix(val maxColumns: Int, val maxRows: Int) {
    private var matrix: MutableList<MutableList<Item?>> = MutableList(maxRows, {
        MutableList<Item?>(maxColumns, { null })
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

    fun getRow(rownum: Int): List<Item?> = matrix.map { column -> column.first() }

    fun getColumn(colnum: Int): List<Item?> = matrix[colnum]

}