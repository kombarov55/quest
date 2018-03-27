package com.ovt.quest.three_in_a_row

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.addClickListener
import com.ovt.quest.main_menu_screens.MainMenuScreen
import com.ovt.quest.main_menu_screens.MinigamesScreen
import com.ovt.quest.three_in_a_row.layout.ThreeInARowStage
import com.ovt.quest.three_in_a_row.model.*
import com.ovt.quest.three_in_a_row.model.Item.Type.*

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowScreen(private val game: QuestGame) : Screen {

    companion object {
        var maxRows = 8
        var maxColumns = 8
    }

    private val stage = ThreeInARowStage(game)
    private val matrix = Matrix(maxColumns, maxRows)

    private val sound = Gdx.audio.newSound(Gdx.files.internal("sounds/shot-and-reload.wav"))

    private val itemFactory = ItemFactory()

    override fun show() {
        addInitialItems()
        Gdx.input.inputProcessor = stage
        stage.onSwap = ::onSwap
        stage.pressMe2.addClickListener {
            val groups = GroupFinder.resolveGroups(matrix)
            if (groups.isNotEmpty()) {
                removeGroups(groups)
            }
        }

        stage.pressMe.addClickListener {
            ItemFall.executeFallDown(matrix, itemFactory)
        }

        stage.pressMe3.addClickListener {
            addNewItems(matrix, itemFactory, stage)
        }

        stage.homeButton.addClickListener {
            game.screen = MainMenuScreen(game)
        }

    }

    private fun onSwap(i1LogicCoords: Pair<Int, Int>, i2LogicCoords: Pair<Int, Int>) {

        val i1 = matrix.get(i1LogicCoords)
        val i2 = matrix.get(i2LogicCoords)

        if (i1 == null || i2 == null) return
        swap(i1, i2, then = {
            val groups = GroupFinder.resolveGroups(matrix)
            updateCounters(groups)
            if (groups.isNotEmpty()) {
                removeLoop(groups, matrix, itemFactory, stage)
            } else {
                swap(i1, i2)
            }
        })
    }

    private fun updateCounters(groups: List<List<Item>>) {
        for (group in groups) {
            val label = when (group.first().type) {
                Red -> stage.redCounter
                Blue -> stage.blueCounter
                Yellow -> stage.yellowCounter
                Pink -> stage.pinkCounter
                Hole -> throw RuntimeException("never gonna happen")
            }

            val prevValue = label.text.toString().drop(1).toInt()
            val newValue = prevValue + group.size
            label.setText("x$newValue")

        }
    }

    private fun removeLoop(matches: List<List<Item>>, matrix: Matrix, itemFactory: ItemFactory, stage: Stage) {
        removeGroups(matches, then = {
            ItemFall.executeFallDown(matrix, itemFactory, then = {
                addNewItems(matrix, itemFactory, stage)
                val newGroups = GroupFinder.resolveGroups(matrix)
                if (newGroups.isNotEmpty()) {
                    updateCounters(newGroups)
                    removeLoop(newGroups, matrix, itemFactory, stage)
                }
            })
        })
    }



    private fun removeGroups(matches: List<List<Item>>, then: () -> Unit = { println("After match remove") }) {
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

         sound.play(0.4f)
    }



    private fun swap(i1: Item, i2: Item, then: () -> Unit = { println("After swap!") }) {
        val (i1col, i1row) = i1.column to i1.row

        i1.fastMoveTo(i2.column, i2.row)
        matrix.put(i1)

        i2.fastMoveTo(i1col, i1row, then)
        matrix.put(i2)
    }

    private fun addNewItems(matrix: Matrix, itemFactory: ItemFactory, stage: Stage, then: () -> Unit = {  }) {
        fun addNew(item: Item) {
            val ii = itemFactory.nonMatchingItem(item.column, item.row, matrix)
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
        for (row in 0 until maxRows) {
            for (column in 0 until maxColumns) {
                val i = itemFactory.nonMatchingItem(column, row, matrix)
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
        sound.dispose()
    }
}