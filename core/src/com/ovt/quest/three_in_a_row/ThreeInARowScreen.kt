package com.ovt.quest.three_in_a_row

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.MathUtils
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.addClickListener
import com.ovt.quest.three_in_a_row.layout.ThreeInARowStage
import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Items
import com.ovt.quest.three_in_a_row.model.MatchResolver
import com.ovt.quest.three_in_a_row.model.Matrix

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
                })
            }
        }

        stage.pressMe.addClickListener {
            for (row in 0 until maxRows) {
                for (column in 0 until maxColumns) {
                    val i = matrix.get(column ,row)
                    if (i == null) {
                        val ii = items.rand(column, row)
                        matrix.put(ii)
                        stage.addActor(ii)
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
            i1.column = i2c
            i1.row = i2r
            matrix.put(i1)

            i2.moveTo(i1c, i1r)
            i2.column = i1c
            i2.row = i1r
            matrix.put(i2)
        }
    }

    private fun addInitialItems() {
        fun findNonMatchingItem(column: Int, row: Int, matrix: Matrix): Item {
            val chosenType = Item.Type.values()[MathUtils.random(Item.Type.values().size - 1)]

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

        for (row in 0..9) {
            for (column in 0..9) {
                val i = findNonMatchingItem(column, row, matrix)

                matrix.put(i, column, row)
            }
        }

        println(findMatches())
        matrix.forEach { stage.addActor(it) }
    }

    private fun findMatches(): MutableList<List<Item>> {
        val matches = mutableListOf<List<Item>>()
        for (row in 0 until maxRows) {
            for (column in 0 until maxColumns - 3) {
                val curr = matrix.get(column, row)!!
                val right1 = matrix.get(column + 1, row)!!
                val right2 = matrix.get(column + 2, row)!!
                if (curr.type == right1.type && curr.type == right2.type) {
                    matches.add(listOf(curr, right1, right2))
                }
            }
        }

        return matches
    }

    private fun findMatches(row: List<Item>): List<List<Item>> {

        fun findNextMatch(requiredType: Item.Type, remainingRow: List<Item>, result: MutableList<Item>): List<Item> {
            val next = remainingRow.firstOrNull()

            if (next != null && next.type == requiredType) {
                result.add(next)
                return findNextMatch(requiredType, remainingRow.subList(1,remainingRow.size), result)
            } else {
                return result
            }

        }

        val matches = mutableListOf<List<Item>>()



        return matches
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