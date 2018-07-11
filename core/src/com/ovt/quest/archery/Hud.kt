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
import com.ovt.quest.horce_racing.layout.FinishTable

class Hud(private val game: QuestGame): Stage() {

    val table = Table()
    val home = game.buttons.imgButton("img/home.png")
    val zoomIn = game.buttons.imgButton("img/zoom-in.png")
    val zoomOut = game.buttons.imgButton("img/zoom-out.png")
    val bowRotation = Slider(0f, 90f, 1f, false, game.skin)
    val bowPower = Slider(0f, Vars.bowMaxPower, 1f, false, game.skin)
    val fire = game.buttons.imgButton("img/explosion.png")
    val createArrow = game.buttons.imgButton("img/up-arrow.png")

    val finishTable = FinishTable(game)

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

        home.addClickListener { ArcheryEvents.goHome.onNext(Unit) }
        zoomIn.addClickListener { ArcheryEvents.zoomCamera.onNext(Vars.zoom) }
        zoomOut.addClickListener { ArcheryEvents.zoomCamera.onNext(-Vars.zoom) }
        bowRotation.addListener(object: ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                ArcheryEvents.bowRotation.onNext(bowRotation.value)
            }
        })

        bowPower.addListener(object: ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                ArcheryEvents.bowPower.onNext(bowPower.value)
            }
        })

        fire.addClickListener { ArcheryEvents.fireBow.onNext(bowRotation.value to bowPower.value) }
        createArrow.addClickListener { ArcheryEvents.createArrow.onNext(Unit) }


        addActor(finishTable)
        addActor(table)
    }

    fun draw(sb: SpriteBatch) {
        table.draw(sb, 1f)
        finishTable.draw(sb, 1f)
    }



}