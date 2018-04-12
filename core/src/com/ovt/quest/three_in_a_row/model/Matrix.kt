package com.ovt.quest.three_in_a_row.model

/**
 * Created by nikolay on 15.03.18.
 */
open class Matrix(val maxColumns: Int, val maxRows: Int) {
    protected var matrix: MutableList<MutableList<Item?>> = MutableList(maxColumns, {
        MutableList<Item?>(maxRows, { null })
    })

    fun put(item: Item, column: Int, row: Int) {
        matrix[column][row] = item
    }

    fun put(item: Item) {
        matrix[item.column][item.row] = item
    }

    open fun get(column: Int, row: Int): Item? {
        try {
            return matrix[column][row]
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }

    open fun fillWith(itemFactory: ItemFactory) {
        for (row in 0 until maxRows) {
            for (column in 0 until maxColumns) {
                val i = itemFactory.nonMatchingItem(column, row, this)
                put(i, column, row)
            }
        }
    }

    fun get(coords: Pair<Int, Int>): Item? = get(coords.first, coords.second)

    fun remove(column: Int, row: Int) {
        matrix[column][row] = null
    }

    fun forEach(f: (Item?) -> Unit) {
        matrix.flatten().forEach(f)
    }

    fun flatten() = matrix.flatten()

    fun print() {
        for (row in maxRows - 1 downTo 0) {
            for (column in 0 until maxColumns) {
                val i = get(column, row)
//                print("${i?.type.toString().first()}")
                print("${i?.type?.name?.let { name -> if (name.equals("Hole")) " " else name.first() }} {${i?.column}, ${i?.row}}\t")
            }
            println()
        }
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    }

    fun getRow(rownum: Int): List<Item?> = matrix.map { column -> column.first() }

    fun getColumn(colnum: Int): List<Item?> = matrix[colnum]

}