package com.ovt.quest.three_in_a_row.model
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.ovt.quest.three_in_a_row.Direction
import com.ovt.quest.three_in_a_row.Direction.*
import com.ovt.quest.three_in_a_row.ThreeInARowScreen
import com.ovt.quest.three_in_a_row.layout.CallbackAction
import com.ovt.quest.three_in_a_row.toPositive

/**
 * Created by nikolay on 15.03.18.
 */
class Item internal constructor (
        var column: Int,
        var row: Int,
        texture: Texture,
        val type: Type
) : Actor() {

    private val textureRegion = TextureRegion(texture, 0, 0, texture.width, texture.height)

    enum class Type {
        Red, Blue, Yellow, Pink, Hole
    }

    init {
        val (x, y) = coords(column, row)
        this.x = x
        this.y = y
        width = itemWidth
        height = itemHeight

        originX = width / 2f
        originY = height / 2f
    }

    fun leftOfSelf(matrix: Matrix): Item? = matrix.get(column - 1, row)
    fun upOfSelf(matrix: Matrix): Item? = matrix.get(column, row + 1)
    fun rightOfSelf(matrix: Matrix): Item? = matrix.get(column + 1, row)
    fun downOfSelf(matrix: Matrix): Item? = matrix.get(column, row - 1)

    fun getNext(direction: Direction, matrix: Matrix): Item? = when(direction) {
        Up -> upOfSelf(matrix)
        Right -> rightOfSelf(matrix)
        Down -> downOfSelf(matrix)
        Left -> leftOfSelf(matrix)
    }

    //TODO: разве разница in 0..1, а не == 1?
    fun isNeighbourTo(i: Item): Boolean =
            (column == i.column && toPositive(row - i.row) in 0..1) ||
            (row == i.row && toPositive(column - i.column) in 0..1)

    fun fastMoveTo(column: Int, row: Int, then: () -> Unit = {  }) {
        this.column = column
        this.row = row

        val (newX, newY) = coords(column, row)

        addAction(
                SequenceAction(
                        Actions.moveTo(newX, newY, swapDuration),
                        CallbackAction(then)))
    }

    fun slowMoveTo(column: Int, row: Int, then: () -> Unit = {  }) {
        this.column = column
        this.row = row

        val (newX, newY) = coords(column, row)
        addAction(SequenceAction(Actions.moveTo(newX, newY, fallDuration, Interpolation.pow2), CallbackAction(then)))
    }

    fun scaleUp() {
        addAction(Actions.scaleBy(scaleAmount, scaleAmount, scaleDuration))
    }

    fun scaleDown() {
        addAction(Actions.scaleBy(-scaleAmount, -scaleAmount, scaleDuration))
    }

    fun popup(then: () -> Unit = { }) {
        addAction(SequenceAction(
                Actions.scaleBy(scaleAmount, scaleAmount, scaleDuration),
                Actions.scaleBy(-scaleAmount, -scaleAmount, scaleDuration),
                CallbackAction(then)))
    }

    fun dissapear(then: () -> Unit) {
        addAction(
                SequenceAction(
                        ParallelAction(
                                Actions.scaleTo(0.1f, 0.1f, dissapearDuration),
                                Actions.fadeOut(dissapearDuration)),
                        CallbackAction(then)))
    }

    fun comeOut() {
        this.setScale(0.1f, 0.1f)
        val rotate = Actions.rotateBy(360f * 1, comeOutDuration)
        val scaleUp = Actions.scaleTo(1f, 1f, comeOutDuration)
        addAction(ParallelAction(rotate, scaleUp))
    }

    private fun coords(column: Int, row: Int): Pair<Float, Float> {
        return tablePadLeft + itemPad + (column * (itemWidth + (itemPad * 2))) to tablePadBottom + itemPad + (row * (itemWidth + (itemPad * 2)))
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.setColor(color.r, color.g, color.b, parentAlpha * color.a)
        batch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
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

        private val itemWidth = ((w - tablePadLeft * 2) / ThreeInARowScreen.maxColumns) - (itemPad * 2)
        private val itemHeight = itemWidth

        val fullItemWidth = itemWidth + itemPad * 2
        val fullItemHeight = itemHeight + itemPad * 2

        val scaleDuration = 0.5f
        private val scaleAmount = 0.15f

        private val swapDuration = 0.15f
        val fallDuration = 0.45f

        val dissapearDuration = 0.25f

        val comeOutDuration = 0.5f
    }
}