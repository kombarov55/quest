package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.addClickListener

class Hud(private val game: QuestGame): Stage() {

    val table = Table()
    val home = game.buttons.imgButton("img/home.png")
    val zoomIn = game.buttons.imgButton("img/zoom-in.png")
    val zoomOut = game.buttons.imgButton("img/zoom-out.png")
    val bowRotation = Slider(0f, 90f, 1f, false, game.skin)
    val bowPower = Slider(0f, Vars.bowMaxPower, 1f, false, game.skin)
    val fire = game.buttons.imgButton("img/explosion.png")
    val createArrow = game.buttons.imgButton("img/up-arrow.png")

    private val w = Gdx.graphics.width
    private val h = Gdx.graphics.height

    init {
        table.debug = true
        table.setPosition(w * 0.12f, h * 0.85f)
        table.defaults().width(h * 0.1f).height(h * 0.1f)
        table.add(home)
        table.add(zoomIn)
        table.add(zoomOut)
        table.row()
        table.add(bowRotation)
        table.add(bowPower)
        table.add(fire)
        table.add(createArrow)

        home.addClickListener { Events.goHome.onNext(Unit) }
        zoomIn.addClickListener { Events.zoomCamera.onNext(Vars.zoom) }
        zoomOut.addClickListener { Events.zoomCamera.onNext(-Vars.zoom) }
        bowRotation.addListener(object: ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                Events.bowRotation.onNext(bowRotation.value)
            }
        })

        bowPower.addListener(object: ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                Events.bowPower.onNext(bowPower.value)
            }
        })

        fire.addClickListener { Events.fireBow.onNext(bowRotation.value to bowPower.value) }
        createArrow.addClickListener { Events.createArrow.onNext(Unit) }


        addActor(table)
    }

    fun draw(sb: SpriteBatch) {
        table.draw(sb, 1f)
    }



}