package com.ovt.quest.three_in_a_row.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.ovt.quest.three_in_a_row.layout.CallbackAction
import com.ovt.quest.three_in_a_row.toPositive

/**
 * Created by nikolay on 15.03.18.
 */
class Item internal constructor (
        var column: Int,
        var row: Int,
        texture: Texture,
        val type: Type,
        private val matrix: Matrix
) : Actor() {

    private val textureRegion = TextureRegion(texture, 0, 0, texture.width, texture.height)

    enum class Type {
        Red, Blue, Yellow
    }

    init {
        matrix.put(this, column, row)
        val (x, y) = coords()
        this.x = x
        this.y = y
        width = itemWidth
        height = itemHeight
    }

    fun left(): Item? = matrix.get(column - 1, row)
    fun up(): Item? = matrix.get(column, row + 1)
    fun right(): Item? = matrix.get(column + 1, row)
    fun down(): Item? = matrix.get(column, row - 1)

    //TODO: разве разница in 0..1, а не == 1?
    fun isNeighbourTo(i: Item): Boolean =
            (column == i.column && toPositive(row - i.row) in 0..1) ||
            (row == i.row && toPositive(column - i.column) in 0..1)

    fun moveTo(column: Int, row: Int) {
        matrix.remove(this.column, this.row)
        matrix.put(this, column, row)
        this.column = column
        this.row = row
        val (newX, newY) = coords()

        addAction(Actions.moveTo(newX, newY, moveDuration))
    }

    fun moveTo(column: Int, row: Int, callback: () -> Unit) {
        matrix.remove(this.column, this.row)
        matrix.put(this, column, row)
        this.column = column
        this.row = row

        val (newX, newY) = coords()

        addAction(SequenceAction(Actions.moveTo(newX, newY, moveDuration), CallbackAction(callback)))
    }

    private val moveInActionX = moveForScale(scaleAmount, width, height).first //scaleAmount * width * -0.5f
    private val moveInActionY = moveForScale(scaleAmount, width, height).second //scaleAmount * height * -0.5f
    private val dissapearMovementX = moveForScale(dissapearScale, width, height).first
    private val dissapearMovementY = moveForScale(dissapearScale, width, height).second

    fun scaleUp() {
        val scaleOut = ParallelAction(
                Actions.scaleBy(scaleAmount, scaleAmount, scaleDuration),
                Actions.moveBy(moveInActionX, moveInActionY, scaleDuration))
        addAction(scaleOut)
    }

    fun scaleDown() {
        val scaleDown = ParallelAction(
                Actions.scaleBy(-scaleAmount, -scaleAmount, scaleDuration),
                Actions.moveBy(-moveInActionX, -moveInActionY, scaleDuration))
        addAction(scaleDown)
    }

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

    fun dissapear() {
        val scale = Actions.scaleBy(dissapearScale, dissapearScale, dissapearDuration)
        val move = Actions.moveBy(dissapearMovementX, dissapearMovementY, dissapearDuration)
        val fade = Actions.fadeOut(dissapearDuration)
        addAction(ParallelAction(fade, scale, move))
    }

    fun comeOut() {
        val fade = Actions.fadeIn(dissapearDuration)
        addAction(fade)
    }

    private fun coords(): Pair<Float, Float> {
        return tablePadLeft + itemPad + (column * (itemWidth + (itemPad * 2))) to tablePadBottom + itemPad + row * (itemWidth + (itemPad * 2))
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.setColor(color.r, color.g, color.b, parentAlpha * color.a)
        batch.draw(textureRegion, x, y, 0f, 0f, width, height, scaleX, scaleY, rotation)
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

        private val dissapearScale = -1f
        private val dissapearDuration = 0.3f

        //private val moveInActionX = scaleAmount * width * -0.5f
        private fun moveForScale(scale: Float, width: Float, height: Float): Pair<Float, Float> {
            return scale * width * -0.5f to scale * height * -0.5f
        }
    }
}