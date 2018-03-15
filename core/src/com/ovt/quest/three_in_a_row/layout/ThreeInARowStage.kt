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
class ThreeInARowStage(game: QuestGame) : Stage() {

    private val itemBlueTexture = Texture(Gdx.files.internal("img/item_blue.png"))
    private val itemRedTexture = Texture(Gdx.files.internal("img/item_red.png"))
    private val itemYellowTexture = Texture(Gdx.files.internal("img/item_yellow.png"))

    private val allTextures = listOf(itemBlueTexture, itemRedTexture, itemYellowTexture)

    private var selectedItem: Item? = null
    private val matrix = Matrix<Item>(10, 10)

    init {
        for (row in 0..9) {
            for (column in 0..9) {
                val t = allTextures[MathUtils.random(allTextures.size - 1)]
                val item = Item(column, row, t)
                addActor(item)
                matrix.add(item, column, row)
            }
        }
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
        val clickedItem = resolveItem(screenX, screenY)

        if (clickedItem != null) {
            moveItemLogic(clickedItem)
        }

        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        return super.touchUp(screenX, screenY, pointer, button)
    }

    private fun resolveItem(x: Int, y: Int): Item? {
        val xFromStart = x - Item.tablePadLeft
        val yFromStart = (Gdx.graphics.height - y) - Item.tablePadBottom

        val column = (xFromStart / Item.fullItemWidth).toInt()
        val row = (yFromStart / Item.fullItemHeight).toInt()

        return matrix.get(column, row)
    }

    private fun moveItemLogic(clickedItem: Item) {
        if (selectedItem == null || selectedItem == clickedItem) {
            clickedItem.popup()
            selectedItem = clickedItem
        } else {
            if (areNeighbours(selectedItem!!, clickedItem)) {
                swap(selectedItem!!, clickedItem)
            }
            selectedItem = null
        }
    }

    private fun areNeighbours(i1: Item, i2: Item): Boolean {
        return (i1.column == i2.column && positive(i1.row - i2.row) in 0..1) ||
                (i1.row == i2.row && positive(i1.row - i2.row) in 0..1)
    }

    private fun swap(i1: Item, i2: Item) {
        val (selCol, selRow) = i1.column to i1.row
        i1.moveTo(i2.column, i2.row)
        i2.moveTo(selCol, selRow)

        matrix.swap(i1.column, i1.row, i2.column, i2.row)
    }

    override fun dispose() {
        allTextures.forEach { it.dispose() }
    }

    private infix fun positive(x: Int): Int = if (x < 0) -x else x
}