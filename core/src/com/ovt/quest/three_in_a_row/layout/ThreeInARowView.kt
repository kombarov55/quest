package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ovt.quest.QuestGame
import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.toPositive

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowView(game: QuestGame): Stage() {

    var onSwap: ((Pair<Int, Int>, Pair<Int, Int>) -> Unit)? = { p1, p2 ->
        println("swap $p1 + $p2")
    }


    init {
        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        val b = game.buttons.biggerButton("press me", {
        })

        b.width = w * 0.8f
        b.height = h * 0.1f
        b.x = (w - b.width) / 2
        b.y = h * 0.7f

        addActor(b)

        val bb = game.buttons.biggerButton("delete row", {
        })

        bb.width = b.width
        bb.height =  b.height
        bb.x = b.x
        bb.y = b.y + b.height + h * 0.01f

        addActor(bb)
    }

    private var selectedItemLogicCoords: Pair<Int, Int>? = null

    private var touchStart: Pair<Int, Int>? =  null

    private fun dragTouchDown(screenX: Int, screenY: Int) {
        touchStart = screenX to screenY

        selectedItemLogicCoords = resolveLogicCoords(touchStart!!)
    }

    private fun dragTouchUp(screenX: Int, screenY: Int) {
        if (selectedItemLogicCoords == null) return
        val direction = resolveDirection(touchStart?.first ?: 0, touchStart?.second ?: 0, screenX, screenY)

        val (selectedColumn, selectedRow) = selectedItemLogicCoords!!

        val i2LogicCoords = when (direction) {
            Direction.UP -> selectedItemLogicCoords?.copy(second = selectedRow + 1)
            Direction.RIGHT -> selectedItemLogicCoords?.copy(first = selectedColumn + 1)
            Direction.DOWN -> selectedItemLogicCoords?.copy(first = selectedRow - 1)
            Direction.LEFT -> selectedItemLogicCoords?.copy(second = selectedColumn - 1)
        }

        onSwap?.invoke(selectedItemLogicCoords!!, i2LogicCoords!!)
    }

    enum class Direction {
        UP, RIGHT, DOWN, LEFT
    }

    private fun resolveDirection(startX: Int, startY: Int, endX: Int, endY: Int): Direction {
        val xDiff = endX - startX
        val yDiff = endY - startY

        // Горизонтальное движение
        if (toPositive(xDiff) > toPositive(yDiff)) {
            return if (xDiff > 0) Direction.RIGHT else Direction.LEFT
        }
        // Вертикальное
        else {
            return if (yDiff < 0) Direction.UP else Direction.DOWN
        }
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        dragTouchDown(screenX, screenY)
        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        dragTouchUp(screenX, screenY)
        return super.touchUp(screenX, screenY, pointer, button)
    }

    private fun resolveLogicCoords(coords: Pair<Int, Int>): Pair<Int, Int>? {
        val (x, y) = coords

        val xFromMatrixStart = x - Item.tablePadLeft
        val yFromMatrixStart = (Gdx.graphics.height - y) - Item.tablePadBottom

        val column = (xFromMatrixStart / Item.fullItemWidth).toInt()
        val row = (yFromMatrixStart / Item.fullItemHeight).toInt()

        return column to row
    }
}