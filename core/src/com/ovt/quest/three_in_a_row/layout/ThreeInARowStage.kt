package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowStage(game: QuestGame) : Stage() {

    private val h = Gdx.graphics.height
    private val w = Gdx.graphics.width

    private val tablePadBottom = h * 0.1f
    private val tablePadLeft = w * 0.05f
    private val itemPad = w * 0.005f

    private val itemWidth = ((w - tablePadLeft * 2) / 10) - (itemPad * 2)
    private val itemHeight = itemWidth

    init {


        // leftOUterPad + pad + item_w
        for (column in 0..9) {


            for (row in 0..9) {
                val (startX, startY) = coords(row, column)
                val item = Item(startX, startY, itemWidth, itemHeight)
                addActor(item)
            }

        }

    }

    private fun coords(row: Int, column: Int): Pair<Float, Float> {
        return itemPad + (tablePadLeft + row * (itemWidth + (itemPad * 2))) to itemPad + (tablePadBottom + column * (itemWidth + (itemPad * 2)))
    }


    class Item(x: Float, y: Float, w: Float, h: Float) : Actor() {

        val texture = Texture(Gdx.files.internal("img/diamond.png"))


        init {
            this.x = x
            this.y = y
            width = w
            height = h
        }

        override fun draw(batch: Batch, parentAlpha: Float) {
            batch.draw(texture, x, y, width, height)
        }
    }
}