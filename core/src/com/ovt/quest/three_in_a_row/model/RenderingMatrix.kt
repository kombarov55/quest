package com.ovt.quest.three_in_a_row.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

class RenderingMatrix(maxColumns: Int, maxRows: Int): Matrix(maxColumns, maxRows) {

    private val h = Gdx.graphics.height
    private val w = Gdx.graphics.width

    private val tableMarginBottom = h * 0.08f
    private val tableMarginTop = h * 0.15f
    private val tableMarginLeft = w * 0.15f

    private val tableHeight = (h - tableMarginBottom - tableMarginTop)
    private val tableWidth = (w - tableMarginLeft * 2)

    private val tableStart = Vector2(tableMarginLeft, tableMarginBottom)

    private val itemPad = w  * 0.0025f

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

    fun project(c: Coords): Vector2 = project(c.x, c.y)

    fun project(i: Item): Vector2 = project(i.column, i.row)

    fun unproject(coords: Vector2): Pair<Int, Int>? {

        val xFromMatrixStart = coords.x - tableMarginLeft
        val yFromMatrixStart = (h - coords.y) - tableMarginBottom

        val column = (xFromMatrixStart / fullItemWidth).toInt()
        val row = (yFromMatrixStart / fullItemHeight).toInt()

        return column to row
    }


}