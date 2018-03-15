package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.ovt.quest.commons.addClickListener

/**
 * Created by nikolay on 15.03.18.
 */
class Item(row: Int, column: Int, private val texture: Texture, callback: (Int, Int) -> Unit) : Actor() {
    init {
        val (x, y) = coords(row, column)
        this.x = x
        this.y = y
        width = itemWidth
        height = itemHeight

        addClickListener {
            callback.invoke(row, column)
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(texture, x, y, width, height)
    }

    private fun coords(row: Int, column: Int): Pair<Float, Float> {
        return tablePadLeft + itemPad + (row * (itemWidth + (itemPad * 2))) to tablePadBottom + itemPad + column * (itemWidth + (itemPad * 2))
    }

    companion object {
        private val h = Gdx.graphics.height
        private val w = Gdx.graphics.width

        private val tablePadBottom = h * 0.1f
        private val tablePadLeft = w * 0.05f
        private val itemPad = w * 0.005f

        private val itemWidth = ((w - tablePadLeft * 2) / 10) - (itemPad * 2)
        private val itemHeight = itemWidth
    }
}