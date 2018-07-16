package com.ovt.quest.commons

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener


/**
 * Created by nikolay on 15.03.18.
 */
fun Actor.addClickListener(f: () -> Unit) {
    addListener(object : ClickListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            f.invoke()
            return super.touchDown(event, x, y, pointer, button)
        }
    })
}

fun TiledMap.width(): Int = properties["width"] as Int * properties.get("tilewidth") as Int
fun TiledMap.height(): Int = properties["height"] as Int * properties.get("tileheight") as Int

fun Rectangle.scale(xScale: Float, yScale: Float) {
    this.x = this.x * xScale
    this.y = this.y * yScale
    this.width = this.width * xScale
    this.height = this.height * yScale
}

fun OrthographicCamera.unproject(v: Vector2): Vector2 {
    val r = this.unproject(Vector3(v, 0f))
    v.set(r.x, r.y)
    return v
}