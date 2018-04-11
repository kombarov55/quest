package com.ovt.quest.three_in_a_row.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

import com.ovt.quest.three_in_a_row.model.Item.Type.*

/**
 * Created by nikolay on 23/03/2018.
 */
class  ItemFactory(private val matrix: Matrix) {

    private val nonHoleTypes = Item.Type.values().filter { it != Hole }

    private val blueTexture = Texture(Gdx.files.internal("img/item_blue.png"))
    private val redTexture = Texture(Gdx.files.internal("img/item_red.png"))
    private val yellowTexture = Texture(Gdx.files.internal("img/item_yellow.png"))
    private val pinkTexture = Texture(Gdx.files.internal("img/item_pink.png"))
    private val holeTexture = Texture(Gdx.files.internal("img/sasha.jpg"))

    val allTextures = listOf(blueTexture, redTexture, yellowTexture, pinkTexture)

    fun red(column: Int, row: Int) = Item(column, row, matrix.translate(column, row), redTexture, Red)
    fun blue(column: Int, row: Int) = Item(column, row, matrix.translate(column, row), blueTexture, Blue)
    fun yellow(column: Int, row: Int) = Item(column, row, matrix.translate(column, row), yellowTexture, Yellow)
    fun pink(column: Int, row: Int) = Item(column, row, matrix.translate(column, row), pinkTexture, Pink)
    fun hole(column: Int, row: Int) = Item(column, row, matrix.translate(column, row), holeTexture, Hole)
    fun rand(column: Int, row: Int) = byType(randType(), column, row)

    fun randType(): Item.Type = nonHoleTypes[MathUtils.random(nonHoleTypes.size - 1)]

    fun byType(type: Item.Type, column: Int, row: Int) = when(type) {
        Red -> red(column ,row)
        Blue -> blue(column, row)
        Yellow -> yellow(column, row)
        Pink -> pink(column, row)
        Hole -> throw RuntimeException("Trying to create a hole")
    }

    fun nonMatchingItem(column: Int, row: Int, matrix: Matrix): Item {
        val chosenType = randType()

        val left1 = matrix.get(column - 1, row)
        val left2 = matrix.get(column - 2, row)

        val down1 = matrix.get(column, row - 1)
        val down2 = matrix.get(column, row - 2)

        if (left1?.type == chosenType && left2?.type == chosenType ||
                down1?.type == chosenType && down2?.type == chosenType) {
            return nonMatchingItem(column, row, matrix)
        } else {
            return byType(chosenType, column, row)
        }
    }
}