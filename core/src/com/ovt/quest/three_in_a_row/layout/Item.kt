package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.*
import com.ovt.quest.commons.addClickListener

/**
 * Created by nikolay on 15.03.18.
 */
class Item(var column: Int, var row: Int, private val texture: Texture, callback: (Item) -> Unit) : Actor() {
    val textureRegion = TextureRegion(texture, 0, 0, texture.width, texture.height)

    init {
        val (x, y) = coords()
        this.x = x
        this.y = y
        width = itemWidth
        height = itemHeight

        addClickListener {
            callback.invoke(this)
        }
    }

    fun moveTo(column: Int, row: Int) {
        this.column = column
        this.row = row

        val (newX, newY) = coords()

        this.x = newX
        this.y = newY
    }

    private val moveInActionX = actionAmount * width * -0.5f
    private val moveInActionY = actionAmount * height * -0.5f

    fun pop() {
        val scaleOut = ParallelAction(
                Actions.scaleBy(actionAmount, actionAmount, actionDuration),
                Actions.moveBy(moveInActionX, moveInActionY, actionDuration)
        )
        val scaleIn = ParallelAction(
                Actions.scaleBy(-actionAmount, -actionAmount, actionDuration),
                Actions.moveBy(-moveInActionX, -moveInActionY, actionDuration))

        addAction(SequenceAction(scaleOut, scaleIn))
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(textureRegion, x, y, 0f, 0f, width, height, scaleX, scaleY, rotation)
    }

    private fun coords(): Pair<Float, Float> {
        return tablePadLeft + itemPad + (column * (itemWidth + (itemPad * 2))) to tablePadBottom + itemPad + row * (itemWidth + (itemPad * 2))
    }

    override fun toString(): String {
        return "{$column $row}"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Item) {
            return other.column == column && other.row == row
        } else
            return false
    }

    companion object {
        private val h = Gdx.graphics.height
        private val w = Gdx.graphics.width

        private val tablePadBottom = h * 0.1f
        private val tablePadLeft = w * 0.05f
        private val itemPad = w * 0.005f

        private val itemWidth = ((w - tablePadLeft * 2) / 10) - (itemPad * 2)
        private val itemHeight = itemWidth

        private val actionDuration = 0.05f
        private val actionAmount = 0.15f
    }
}