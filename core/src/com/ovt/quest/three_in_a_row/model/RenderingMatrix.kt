package com.ovt.quest.three_in_a_row.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import kotlin.math.max

class RenderingMatrix(maxColumns: Int, maxRows: Int): Matrix(maxColumns, maxRows) {

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


}