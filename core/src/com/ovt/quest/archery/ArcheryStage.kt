package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ovt.quest.QuestGame

/**
 * Created by nikolay on 28.03.18.
 */
class ArcheryStage(game: QuestGame) : Stage() {

    private val bg = Image(Texture(Gdx.files.internal("img/fon-town-1.jpg")))


    init {
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        bg.x = 0f
        bg.y = 0f
        bg.width = w
        bg.height = h

        addActor(bg)
    }

}