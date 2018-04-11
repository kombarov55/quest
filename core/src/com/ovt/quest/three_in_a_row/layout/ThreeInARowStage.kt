package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.ovt.quest.QuestGame
import com.ovt.quest.three_in_a_row.Direction
import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Matrix
import com.ovt.quest.three_in_a_row.toPositive

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowStage(
        private val game: QuestGame,
        private val matrix: Matrix
): Stage() {

    var onSwap: ((Pair<Int, Int>, Pair<Int, Int>) -> Unit)? = { p1, p2 ->
        println("swap $p1 + $p2")
    }

    val pressMe: Button
    val pressMe2: Button
    val pressMe3: Button

    val homeButton: ImageButton

    val redCounter: Label
    val blueCounter: Label
    val yellowCounter: Label
    val pinkCounter: Label


    init {
        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        pressMe = game.buttons.normalButton("press me")

        pressMe.width = w * 0.8f
        pressMe.height = h * 0.05f
        pressMe.x = (w - pressMe.width) / 2
        pressMe.y = h * 0.7f

        addActor(pressMe)

        pressMe2 = game.buttons.normalButton("press me2")
        pressMe2.width = pressMe.width
        pressMe2.height = pressMe.height
        pressMe2.x = pressMe.x
        pressMe2.y = pressMe.y + pressMe.height + h * 0.01f

        addActor(pressMe2)

        pressMe3 = game.buttons.normalButton("press me3")
        pressMe3.width = pressMe.width
        pressMe3.height = pressMe.height
        pressMe3.x = pressMe.x
        pressMe3.y = pressMe2.y + pressMe2.height + h * 0.01f

        addActor(pressMe3)

        homeButton = game.buttons.imgButton(src = "img/home.png")

        fun placeHorizontally(xs: List<Actor>, pad: Float, side: Float, marginX: Float, marginY: Float) {
            for (i in 0 until xs.size) {
                val actor = xs[i]
                actor.width = side
                actor.height = side
                actor.x = marginX + pad + i * (side + pad * 2)
                actor.y = marginY
            }
        }
        val redCounterImg = Image(Texture(Gdx.files.internal("img/item_red.png")))
        val blueCounterImg = Image(Texture(Gdx.files.internal("img/item_blue.png")))
        val yellowCounterImg = Image(Texture(Gdx.files.internal("img/item_yellow.png")))
        val pinkCounterImg = Image(Texture(Gdx.files.internal("img/item_pink.png")))

        redCounter = game.labelFactory.smallerLabel("x0")
        blueCounter = game.labelFactory.smallerLabel("x0")
        yellowCounter = game.labelFactory.smallerLabel("x0")
        pinkCounter = game.labelFactory.smallerLabel("x0")

        val row = listOf<Actor>(homeButton, redCounterImg, redCounter, blueCounterImg, blueCounter, yellowCounterImg, yellowCounter, pinkCounterImg, pinkCounter)
        placeHorizontally(row, w * 0.005f, w * 0.07f, w * 0.01f, h * 0.95f)
        row.forEach { addActor(it) }
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