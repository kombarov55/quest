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
                val item = Item(column, row, t, ::onItemClick)
                addActor(item)
                matrix.add(item, column, row)
            }
        }
    }

    private fun onItemClick(item: Item) {
        if (selectedItem == null) {
            selectedItem = item
        } else {
            if (areNeighbours(selectedItem!!, item)) {
                swap(selectedItem!!, item)
            }
            selectedItem = null
        }
    }

    private fun areNeighbours(i1: Item, i2: Item): Boolean {
        return (i1.column == i2.column && toPos(i1.row - i2.row) in 0..1) ||
                (i1.row == i2.row && toPos(i1.row - i2.row) in 0..1)
    }

    private fun swap(i1: Item, i2: Item) {
        val (selCol, selRow) = i1.column to i1.row
        i1.moveTo(i2.column, i2.row)
        i2.moveTo(selCol, selRow)
    }

    override fun dispose() {
        allTextures.forEach { it.dispose() }
    }

    private infix fun toPos(x: Int): Int = if (x < 0) -x else x
}