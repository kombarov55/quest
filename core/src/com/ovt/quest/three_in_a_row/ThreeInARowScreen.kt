package com.ovt.quest.three_in_a_row

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
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



    private val itemFactory = ItemFactory()

    override fun show() {
        addInitialItems()
        Gdx.input.inputProcessor = stage
        stage.onSwap = ::onSwap
        stage.pressMe2.addClickListener {
            val matches = MatchResolver.resolveMatches(matrix)
            if (matches.isNotEmpty()) {
                removeMatches(matches)
                println("after matches removed")
                matrix.print()
            }
        }

        stage.pressMe.addClickListener {
            ItemFall.executeFallDown(matrix, itemFactory)
            println("after fall")
            matrix.print()
        }

        stage.pressMe3.addClickListener {
            replaceHoles(matrix, itemFactory, stage)
            println("after replace!")
            matrix.print()
        }

        stage.pressMe4.addClickListener {
            matrix.print()
        }

    }

    private fun onSwap(i1LogicCoords: Pair<Int, Int>, i2LogicCoords: Pair<Int, Int>) {

        val i1 = matrix.get(i1LogicCoords)
        val i2 = matrix.get(i2LogicCoords)

        if (i1 == null || i2 == null) return
        swap(i1, i2, then = {
            println("After swap: ")
            matrix.print()
            val matches = MatchResolver.resolveMatches(matrix)
            if (matches.isNotEmpty()) {
                println("matches: $matches")
                removeMatches(matches, then = {
                    println("after matches removed")
                    matrix.print()
                    ItemFall.executeFallDown(matrix, itemFactory, then = {
                        println("after fall")
                        matrix.print()
                        replaceHoles(matrix, itemFactory, stage)
                    })
                })
            } else {
                swap(i1, i2)
            }
        })
    }



    private fun removeMatches(matches: List<List<Item>>, then: () -> Unit = { println("After match remove") }) {
        fun remove(it: Item) {

        }

        val flattened = matches.flatten()

        flattened.dropLast(1).forEach {
            matrix.put(itemFactory.hole(it.column, it.row))
            it.dissapear(then = {
                it.remove()
            })
        }

        flattened.last().let { first ->
            matrix.put(itemFactory.hole(first.column, first.row))
            first.dissapear(then = {
                first.remove()
                then.invoke()
            })
        }
    }



    private fun swap(i1: Item, i2: Item, then: () -> Unit = { println("After swap!") }) {
        val (i1col, i1row) = i1.column to i1.row

        i1.fastMoveTo(i2.column, i2.row)
        matrix.put(i1)

        i2.fastMoveTo(i1col, i1row, then)
        matrix.put(i2)
    }

    private fun findNonMatchingItem(column: Int, row: Int, matrix: Matrix): Item {
        val chosenType = itemFactory.randType()

        val left1 = matrix.get(column - 1, row)
        val left2 = matrix.get(column - 2, row)

        val down1 = matrix.get(column, row - 1)
        val down2 = matrix.get(column, row - 2)

        if (left1?.type == chosenType && left2?.type == chosenType ||
                down1?.type == chosenType && down2?.type == chosenType) {
            return findNonMatchingItem(column, row, matrix)
        } else {
            return itemFactory.byType(chosenType, column, row)
        }
    }

    private fun replaceHoles(matrix: Matrix, itemFactory: ItemFactory, stage: Stage, then: () -> Unit = {  }) {

        fun addNew(item: Item) {
            val ii = findNonMatchingItem(item.column, item.row, matrix)
            matrix.put(ii)
            stage.addActor(ii)
            ii.comeOut()
        }

        val holes = matrix.flatten().filter { it?.type == Hole }

        if (holes.isNotEmpty()) {

            holes.take(holes.size - 1).forEach { item ->
                addNew(item!!)
            }

            holes.last().let { item ->
                addNew(item!!)
                then.invoke()
            }
        }
    }

    private fun addInitialItems() {
        for (row in 0..9) {
            for (column in 0..9) {
                val i = findNonMatchingItem(column, row, matrix)
                matrix.put(i, column, row)
                stage.addActor(i)
            }
        }
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
        itemFactory.allTextures.forEach { it.dispose() }
    }
}