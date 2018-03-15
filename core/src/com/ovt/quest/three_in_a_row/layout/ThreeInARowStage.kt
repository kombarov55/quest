package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowStage(game: QuestGame) : Stage() {

    private val itemBlueTexture = Texture(Gdx.files.internal("img/item_blue.png"))
    private val itemRedTexture = Texture(Gdx.files.internal("img/item_red.png"))
    private val itemYellowTexture = Texture(Gdx.files.internal("img/item_yellow.png"))

    private val allTextures = listOf(itemBlueTexture, itemRedTexture, itemYellowTexture)

    private var selectedItem: Item? = null

    init {
        for (column in 0..9) {
            for (row in 0..9) {
                val shouldSpawn = MathUtils.random() < 0.9f
                if (!shouldSpawn) continue

                val t = allTextures[MathUtils.random(allTextures.size - 1)]

                addActor(Item(row, column, t, ::onItemClick))
            }
        }
    }

    private fun onItemClick(row: Int, column: Int) {
        println("item clicked::::::{$row, $column}")
    }

    override fun dispose() {
        allTextures.forEach { it.dispose() }
    }
}