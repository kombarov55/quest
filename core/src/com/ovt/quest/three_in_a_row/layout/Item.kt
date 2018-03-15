package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

/**
 * Created by nikolay on 15.03.18.
 */
class Item(var column: Int, var row: Int, private val texture: Texture) : Actor() {
    val textureRegion = TextureRegion(texture, 0, 0, texture.width, texture.height)

    init {
        val (x, y) = coords()
        this.x = x
        this.y = y
        width = itemWidth
        height = itemHeight
    }



    fun moveTo(column: Int, row: Int) {
        this.column = column
        this.row = row

        val (newX, newY) = coords()

        addAction(Actions.moveTo(newX, newY, moveDuration))
    }

    private val moveInActionX = scaleAmount * width * -0.5f
    private val moveInActionY = scaleAmount * height * -0.5f

    fun popup() {
        val scaleOut = ParallelAction(
                Actions.scaleBy(scaleAmount, scaleAmount, scaleDuration),
                Actions.moveBy(moveInActionX, moveInActionY, scaleDuration)
        )
        val scaleIn = ParallelAction(
                Actions.scaleBy(-scaleAmount, -scaleAmount, scaleDuration),
                Actions.moveBy(-moveInActionX, -moveInActionY, scaleDuration))

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

        val tablePadBottom = h * 0.1f
        val tablePadLeft = w * 0.05f
        private val itemPad = w * 0.005f

        private val itemWidth = ((w - tablePadLeft * 2) / 10) - (itemPad * 2)
        private val itemHeight = itemWidth

        val fullItemWidth = itemWidth + itemPad * 2
        val fullItemHeight = itemHeight + itemPad * 2

        private val scaleDuration = 0.05f
        private val scaleAmount = 0.15f

        private val moveDuration = 0.07f
    }
}