package com.ovt.quest.three_in_a_row

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.ovt.quest.three_in_a_row.layout.Item

/**
 * Created by nikolay on 16/03/2018.
 */
class Items {

    private val itemBlueTexture = Texture(Gdx.files.internal("img/item_blue.png"))
    private val itemRedTexture = Texture(Gdx.files.internal("img/item_red.png"))
    private val itemYellowTexture = Texture(Gdx.files.internal("img/item_yellow.png"))

    val allTextures = listOf(itemBlueTexture, itemRedTexture, itemYellowTexture)

    fun red(column: Int, row: Int) = Item(column, row, itemRedTexture, Item.Type.Red)
    fun blue(column: Int, row: Int) = Item(column, row, itemBlueTexture, Item.Type.Blue)
    fun yellow(column: Int, row: Int) = Item(column, row, itemYellowTexture, Item.Type.Red)
    fun rand(column: Int, row: Int) = when (MathUtils.random(0, 2)) {
        0 -> red(column, row)
        1 -> blue(column, row)
        2 -> yellow(column, row)
        else -> red(column, row)
    }

}