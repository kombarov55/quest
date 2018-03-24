package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.ovt.quest.QuestGame
import com.ovt.quest.three_in_a_row.Direction
import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.toPositive

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowStage(game: QuestGame): Stage() {

    var onSwap: ((Pair<Int, Int>, Pair<Int, Int>) -> Unit)? = { p1, p2 ->
        println("swap $p1 + $p2")
    }

    var pressMe: Button
    var pressMe2: Button


    init {
        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        pressMe = game.buttons.biggerButton("press me")

        pressMe.width = w * 0.8f
        pressMe.height = h * 0.1f
        pressMe.x = (w - pressMe.width) / 2
        pressMe.y = h * 0.7f

        addActor(pressMe)

        pressMe2 = game.buttons.biggerButton("press me2")
        pressMe2.width = pressMe.width
        pressMe2.height = pressMe.height
        pressMe2.x = pressMe.x
        pressMe2.y = pressMe.y + pressMe.height + h * 0.05f

        addActor(pressMe2)
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
            Direction.Up -> selectedItemLogicCoords?.copy(second = selectedRow + 1)
            Direction.Down -> selectedItemLogicCoords?.copy(second = selectedRow - 1)
            Direction.Right -> selectedItemLogicCoords?.copy(first = selectedColumn + 1)
            Direction.Left -> selectedItemLogicCoords?.copy(first = selectedColumn - 1)
        }

        onSwap?.invoke(selectedItemLogicCoords!!, i2LogicCoords!!)
    }

    private fun resolveDirection(startX: Int, startY: Int, endX: Int, endY: Int): Direction {
        val xDiff = endX - startX
        val yDiff = endY - startY

        // Горизонтальное движение
        if (toPositive(xDiff) > toPositive(yDiff)) {
            return if (xDiff > 0) Direction.Right else Direction.Left
        }
        // Вертикальное
        else {
            return if (yDiff < 0) Direction.Up else Direction.Down
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