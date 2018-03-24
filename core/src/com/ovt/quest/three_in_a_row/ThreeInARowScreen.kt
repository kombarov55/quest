package com.ovt.quest.three_in_a_row

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.addClickListener
import com.ovt.quest.three_in_a_row.layout.ThreeInARowStage
import com.ovt.quest.three_in_a_row.model.*
import com.ovt.quest.three_in_a_row.model.Item.Type.*

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowScreen(game: QuestGame) : Screen {

    private val maxRows = 10
    private val maxColumns = 10

    private val stage = ThreeInARowStage(game)
    private val matrix = Matrix(maxColumns, maxRows)



    private val items = Items()

    override fun show() {
        addInitialItems()
        Gdx.input.inputProcessor = stage
        stage.onSwap = ::onSwap
        stage.pressMe2.addClickListener {
            MatchResolver.resolveMatches(matrix).flatten().forEach {
                it.popup(then = {
                    it.dissapear()
                    matrix.remove(it.column, it.row)
                    it.remove()

                    matrix.put(items.hole(it.column, it.row))
                })
            }
        }

        stage.pressMe.addClickListener {
            ItemFall.executeFallDown(matrix, items)
        }

        stage.pressMe3.addClickListener {
            for (row in 0 until maxRows) {
                for (column in 0 until maxColumns) {
                    val i = matrix.get(column, row)!!
                    if (i.type == Hole) {
                        val ii = items.rand(i.column, i.row)
                        matrix.put(ii)
                        stage.addActor(ii)
                        ii.comeOut()
                    }
                }
            }
        }

    }

    private fun onSwap(i1LogicCoords: Pair<Int, Int>, i2LogicCoords: Pair<Int, Int>) {
        val (i1c, i1r) = i1LogicCoords
        val (i2c, i2r) = i2LogicCoords

        val i1 = matrix.get(i1c, i1r)
        val i2 = matrix.get(i2c, i2r)

        if (i1 != null && i2 != null) {
            i1.moveTo(i2c, i2r)
            matrix.put(i1)

            i2.moveTo(i1c, i1r)
            matrix.put(i2)
        }
    }

    private fun findNonMatchingItem(column: Int, row: Int, matrix: Matrix): Item {
        val chosenType = items.randType()

        val left1 = matrix.get(column - 1, row)
        val left2 = matrix.get(column - 2, row)

        val down1 = matrix.get(column, row - 1)
        val down2 = matrix.get(column, row - 2)

        if (left1?.type == chosenType && left2?.type == chosenType ||
                down1?.type == chosenType && down2?.type == chosenType) {
            return findNonMatchingItem(column, row, matrix)
        } else {
            return items.byType(chosenType, column, row)
        }
    }

    private fun addInitialItems() {
        for (row in 0..9) {
            for (column in 0..9) {
                val i = findNonMatchingItem(column, row, matrix)

                matrix.put(i, column, row)
            }
        }

        matrix.forEach { stage.addActor(it) }
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.input.inputProcessor = stage

        stage.act()
        stage.draw()
    }

    override fun hide() {
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {
        items.allTextures.forEach { it.dispose() }
    }
}