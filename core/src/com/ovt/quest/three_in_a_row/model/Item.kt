package com.ovt.quest.three_in_a_row.model
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
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
        initialCoords: Vector2,
        itemWidth: Float,
        itemHeight: Float,
        texture: Texture,
        val type: Type
) : Actor() {

    private val textureRegion = TextureRegion(texture, 0, 0, texture.width, texture.height)

    enum class Type {
        Red, Blue, Yellow, Pink, Hole
    }

    init {
        this.x = initialCoords.x
        this.y = initialCoords.y
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

    fun setLogicCoords(column: Int, row: Int) {
        this.column = column
        this.row = row
    }

    fun fastMoveTo(coords: Vector2, then: () -> Unit = {  }) {
        addAction(
                SequenceAction(
                        Actions.moveTo(coords.x, coords.y, swapDuration),
                        CallbackAction(then)))
    }

    fun slowMoveTo(coords: Vector2, then: () -> Unit = {  }) {
        addAction(
                SequenceAction(
                        Actions.moveTo(coords.x, coords.y, fallDuration, Interpolation.pow2),
                        CallbackAction(then)))
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
        val rotate = Actions.rotateBy(360f * -1, comeOutDuration)
        val scaleUp = Actions.scaleTo(1f, 1f, comeOutDuration)
        addAction(ParallelAction(rotate, scaleUp))
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
        val scaleDuration = 0.5f
        private val scaleAmount = 0.15f

        private val swapDuration = 0.15f
        val fallDuration = 0.45f

        val dissapearDuration = 0.25f

        val comeOutDuration = 0.3f
    }
}