package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ovt.quest.commons.QuestGame

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowStage(game: QuestGame) : Stage() {



    init {
        val h = Gdx.graphics.height
        val w = Gdx.graphics.width

        // leftOUterPad + pad + item_w
        for (j in 1..10) {
            val leftOuterPad = w * 0.01f
            val item_w = ((w * 0.95f) / 10) * 0.90f
            val itemPad = ((w * 0.95f) / 10) * 0.05f

            for (i in 1..10) {
                val item = Item(leftOuterPad + i * (item_w), h * 0.2f + j * (item_w), item_w, item_w)
                addActor(item)
            }

        }

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