package com.ovt.quest.three_in_a_row.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
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

    private val tableMarginBottom = h * 0.1f
    private val tableMarginTop = h * 0.2f
    private val tableMarginLeft = w * 0.3f

    private val tableHeight = (h - tableMarginBottom - tableMarginTop)
    private val tableWidth = (w - tableMarginLeft * 2)
    private val tableStart = Vector2(tableMarginLeft, tableMarginBottom)

    private val itemPad = w  * 0.005f

    val itemHeight = (tableHeight / maxRows) - (itemPad * 2)
    val itemWidth = (tableWidth / maxColumns) - (itemPad * 2)

    private val fullItemWidth = itemWidth + itemPad * 2
    private val fullItemHeight = itemHeight + itemPad * 2

    fun project(column: Int, row: Int): Vector2 {
        return Vector2(
                tableStart.x + itemPad + (column * (itemWidth + (itemPad * 2))),
                tableStart.y + itemPad + (row * (itemHeight + (itemPad * 2)))
        )
    }

    fun unproject(coords: Vector2): Pair<Int, Int>? {

        val xFromMatrixStart = coords.x - tableMarginLeft
        val yFromMatrixStart = (h - coords.y) - tableMarginBottom

        val column = (xFromMatrixStart / fullItemWidth).toInt()
        val row = (yFromMatrixStart / fullItemHeight).toInt()

        return column to row
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