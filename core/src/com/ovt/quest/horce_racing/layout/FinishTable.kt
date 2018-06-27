package com.ovt.quest.horce_racing.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Table


class FinishTable: Table() {

    init {
        debug = true
        width = Gdx.graphics.width * 0.5f
        height = Gdx.graphics.height * 0.3f
        defaults().expandX()


    }

}