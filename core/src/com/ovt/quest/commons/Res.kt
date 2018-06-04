package com.ovt.quest.commons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

//TODO: lazy initialization
object Res {

    var explosion: Texture? = Texture(Gdx.files.internal("img/explosion.png"))
}