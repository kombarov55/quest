package com.ovt.quest.quest.commons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture


object R {

    private val textures = mutableMapOf<String, Texture>()

    fun getTexture(name: String): Texture {
        val path = "img/$name"
        var t = textures[path]
        if (t == null) {
           t = Texture(Gdx.files.internal(path))
            textures[path] = t
        }
        return t
    }
}