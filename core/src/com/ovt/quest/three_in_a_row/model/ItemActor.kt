package com.ovt.quest.three_in_a_row.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.ovt.quest.three_in_a_row.layout.CallbackAction
import io.reactivex.Observable

class ItemActor(
        initialCoords: Vector2,
        itemWidth: Float,
        itemHeight: Float,
        texture: Texture
): Actor() {

    private val textureRegion = TextureRegion(texture, 0, 0, texture.width, texture.height)

    init {
        this.x = initialCoords.x
        this.y = initialCoords.y
        width = itemWidth
        height = itemHeight

        originX = width / 2f
        originY = height / 2f
    }

    fun fastMoveTo(coords: Vector2, then: () -> Unit = {  }) {
        addAction(
                SequenceAction(
                        Actions.moveTo(coords.x, coords.y, swapDuration),
                        CallbackAction(then)))
    }

    fun RX_fastMoveTo(coords: Vector2): Observable<Unit> {
        return Observable.create { s ->
            addAction(
                    SequenceAction(
                            Actions.moveTo(coords.x, coords.y, swapDuration),
                            CallbackAction {
                                println("callback")
                                s.onNext(Unit)
                                s.onComplete()
                            }))
        }
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

    fun comeOut(then: (() -> Unit)? = null) {
        this.setScale(0.1f, 0.1f)
        val rotate = Actions.rotateBy(360f * -1, comeOutDuration)
        val scaleUp = Actions.scaleTo(1f, 1f, comeOutDuration)
        val action = if (then == null)
            ParallelAction(rotate, scaleUp) else
            SequenceAction(ParallelAction(rotate, scaleUp), CallbackAction(then))
        addAction(action)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.setColor(color.r, color.g, color.b, parentAlpha * color.a)
        batch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
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