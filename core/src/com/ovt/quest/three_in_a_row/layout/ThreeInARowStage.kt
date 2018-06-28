package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.Direction
import com.ovt.quest.three_in_a_row.model.RenderingMatrix
import com.ovt.quest.three_in_a_row.toPositive

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowStage(
        private val game: QuestGame,
        private val matrix: RenderingMatrix
): Stage() {

    var onSwap: ((Pair<Int, Int>, Pair<Int, Int>) -> Unit)? = { p1, p2 ->
        println("swap $p1 + $p2")
    }

    private var selectedItemLogicCoords: Pair<Int, Int>? = null

    private var touchStart: Vector2? =  null

    private fun dragTouchDown(screenX: Int, screenY: Int) {
        touchStart = Vector2(screenX.toFloat(), screenY.toFloat())

        selectedItemLogicCoords = matrix.unproject(touchStart!!)
    }

    private fun dragTouchUp(screenX: Int, screenY: Int) {
        if (selectedItemLogicCoords == null) return
        val direction = resolveDirection(touchStart?.x ?: 0f, touchStart?.y ?: 0f, screenX.toFloat(), screenY.toFloat())

        val (selectedColumn, selectedRow) = selectedItemLogicCoords!!

        val i2LogicCoords = when (direction) {
            Direction.Up -> selectedItemLogicCoords?.copy(second = selectedRow + 1)
            Direction.Down -> selectedItemLogicCoords?.copy(second = selectedRow - 1)
            Direction.Right -> selectedItemLogicCoords?.copy(first = selectedColumn + 1)
            Direction.Left -> selectedItemLogicCoords?.copy(first = selectedColumn - 1)
        }

        onSwap?.invoke(selectedItemLogicCoords!!, i2LogicCoords!!)
    }

    private fun resolveDirection(startX: Float, startY: Float, endX: Float, endY: Float): Direction {
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
}