package com.ovt.quest.three_in_a_row.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils

import com.ovt.quest.three_in_a_row.model.Item.Type.*

/**
 * Created by nikolay on 23/03/2018.
 */
class ItemFactory {

    private val nonHoleTypes = Item.Type.values().filter { it != Hole }

    private val blueTexture = Texture(Gdx.files.internal("img/item_blue.png"))
    private val redTexture = Texture(Gdx.files.internal("img/item_red.png"))
    private val yellowTexture = Texture(Gdx.files.internal("img/item_yellow.png"))
    private val pinkTexture = Texture(Gdx.files.internal("img/item_pink.png"))
    private val holeTexture = Texture(Gdx.files.internal("img/sasha.jpg"))

    val allTextures = listOf(blueTexture, redTexture, yellowTexture, pinkTexture)

    fun red(column: Int, row: Int) = Item(column, row, redTexture, Red)
    fun blue(column: Int, row: Int) = Item(column, row, blueTexture, Blue)
    fun yellow(column: Int, row: Int) = Item(column, row, yellowTexture, Yellow)
    fun pink(column: Int, row: Int) = Item(column, row, pinkTexture, Pink)
    fun hole(column: Int, row: Int) = Item(column, row, holeTexture, Hole)
    fun rand(column: Int, row: Int) = byType(randType(), column, row)

    fun randType(): Item.Type = nonHoleTypes[MathUtils.random(nonHoleTypes.size - 1)]

    fun byType(type: Item.Type, column: Int, row: Int) = when(type) {
        Red -> red(column ,row)
        Blue -> blue(column, row)
        Yellow -> yellow(column, row)
        Pink -> pink(column, row)
        Hole -> throw RuntimeException("Trying to create a hole")
    }

}