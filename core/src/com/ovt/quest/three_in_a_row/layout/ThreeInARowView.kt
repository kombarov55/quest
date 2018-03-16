package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ovt.quest.QuestGame
import com.ovt.quest.three_in_a_row.Matrix

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowView(game: QuestGame) : Stage() {

    fun addItem(i: Item) {
        addActor(i)
        i.comeOut()
        matrix.add(i)
    }

    fun remove(i: Item) {
        i.dissapear()
        matrix.remove(i.column, i.row)
    }

    var onSwap: ((Item, Item) -> Unit)? = { i1, i2 ->
        remove(i1)
    }


    private var selectedItem: Item? = null
    private var swappedItems: Pair<Item, Item>? = null

    private val matrix = Matrix(10, 10)

    init {

    }

    private var touchStartX: Int? = null
    private var touchStartY: Int? = null

    private fun registerTouchDown(screenX: Int, screenY: Int) {
        println("touch down: $screenX, $screenY")
        touchStartX = screenX
        touchStartY = screenY
    }

    enum class Direction {
        UP, RIGHT, DOWN, LEFT
    }

    private fun resolveDirection(startX: Int, startY: Int, endX: Int, endY: Int): Direction {
        val xDiff = endX - startX
        val yDiff = endY - startY

        // Горизонтальное движение
        if (positive(xDiff) > positive(yDiff)) {
            return if (xDiff > 0) Direction.RIGHT else Direction.LEFT
        }
        // Вертикальное
        else {
            return if (yDiff < 0) Direction.UP else Direction.DOWN
        }
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        dragTouchDown(screenX, screenY)
//        val clickedItem = resolveItem(screenX, screenY)
//        if (clickedItem != null) {
//            if (selectedItem == null) {
//                selectedItem = clickedItem
//                clickedItem.popup()
//            } else {
//                if (selectedItem == clickedItem) {
//                    clickedItem.popup()
//                } else {
//                    if (areNeighbours(selectedItem!!, clickedItem)) {
//                        swap(selectedItem!!, clickedItem)
//                        selectedItem = null
//                    } else {
//                        selectedItem = null
//                    }
//                }
//
//            }
//        }
        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        drawTouchUp(screenX, screenY)
        return super.touchUp(screenX, screenY, pointer, button)
    }



    private fun twoClickTouchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val clickedItem = resolveItem(screenX, screenY)
        if (clickedItem != null) {
            if (selectedItem == null) {
                selectedItem = clickedItem
                clickedItem.popup()
            } else {
                if (selectedItem == clickedItem) {
                    clickedItem.popup()
                } else {
                    if (areNeighbours(selectedItem!!, clickedItem)) {
                        swap(selectedItem!!, clickedItem)
                        selectedItem = null
                    } else {
                        selectedItem = null
                    }
                }

            }
        }

        return true
    }

    private fun dragTouchDown(screenX: Int, screenY: Int) {
        touchStartX = screenX
        touchStartY = screenY

        selectedItem = resolveItem(touchStartX!!, touchStartY!!)
    }

    private fun drawTouchUp(screenX: Int, screenY: Int) {
        if (selectedItem == null) return
        val direction = resolveDirection(touchStartX ?: 0, touchStartY ?: 0, screenX, screenY)
        val i = when (direction) {
            Direction.UP -> matrix.upper(selectedItem!!)
            Direction.RIGHT -> matrix.rightOf(selectedItem!!)
            Direction.DOWN -> matrix.below(selectedItem!!)
            Direction.LEFT -> matrix.leftOf(selectedItem!!)
        }

        if (i != null) {
            swap(selectedItem!!, i)
        }
    }

    private fun resolveItem(x: Int, y: Int): Item? {
        val xFromStart = x - Item.tablePadLeft
        val yFromStart = (Gdx.graphics.height - y) - Item.tablePadBottom

        val column = (xFromStart / Item.fullItemWidth).toInt()
        val row = (yFromStart / Item.fullItemHeight).toInt()

        return matrix.get(column, row)
    }

    private fun areNeighbours(i1: Item, i2: Item): Boolean {
        return (i1.column == i2.column && positive(i1.row - i2.row) in 0..1) ||
                (i1.row == i2.row && positive(i1.column - i2.column) in 0..1)
    }

    private fun swap(i1: Item, i2: Item) {
        val (selCol, selRow) = i1.column to i1.row
        i1.moveTo(i2.column, i2.row)
        i2.moveTo(selCol, selRow, ::onSwap)

        swappedItems = i1 to i2
        matrix.swap(i1.column, i1.row, i2.column, i2.row)
    }

    private fun onSwap() {
        if (swappedItems != null) {
            onSwap?.invoke(swappedItems!!.first, swappedItems!!.second)
        }

        swappedItems = null
    }

    private infix fun positive(x: Int): Int = if (x < 0) -x else x
}