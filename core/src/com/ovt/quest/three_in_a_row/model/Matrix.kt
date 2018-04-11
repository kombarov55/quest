package com.ovt.quest.three_in_a_row.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

/**
 * Created by nikolay on 15.03.18.
 */
class Matrix(val maxColumns: Int, val maxRows: Int) {
    private var matrix: MutableList<MutableList<Item?>> = MutableList(maxRows, {
        MutableList<Item?>(maxColumns, { null })
    })

    private val h = Gdx.graphics.height
    private val w = Gdx.graphics.width

    val tablePadBottom = h * 0.1f
    val tablePadLeft = w * 0.05f
    private val itemPad = w * 0.005f

    private val itemWidth = ((w - tablePadLeft * 2) / maxColumns) - (itemPad * 2)
    private val itemHeight = itemWidth

    val fullItemWidth = itemWidth + itemPad * 2
    val fullItemHeight = itemHeight + itemPad * 2

    fun translate(column: Int, row: Int): Vector2 {
        return Vector2(
                tablePadLeft + itemPad + (column * (itemWidth + (itemPad * 2))),
                tablePadBottom + itemPad + (row * (itemWidth + (itemPad * 2)))
        )
    }

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
                print("${i?.type?.name?.let { name -> if (name.equals("Hole")) " " else name.first() }} {${i?.column}, ${i?.row}}\t")
            }
            println()
        }
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    }

    fun getRow(rownum: Int): List<Item?> = matrix.map { column -> column.first() }

    fun getColumn(colnum: Int): List<Item?> = matrix[colnum]

}