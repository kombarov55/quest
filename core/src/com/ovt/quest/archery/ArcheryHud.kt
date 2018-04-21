package com.ovt.quest.archery

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.addClickListener

class ArcheryHud(private val game: QuestGame, private val screen: ArcheryScreen): Stage() {


    private val homeButton = game.buttons.imgButton("img/home.png")
    private val zoomInButton = game.buttons.imgButton("img/up-arrow.png")
    private val zoomOutButton = game.buttons.imgButton("img/down-arrow.png")

    val slider: Slider = Slider(0f, 90f, 1f, true, game.skin)


    private val hudTable = Table()

    init {
        hudTable.x = Gdx.graphics.width * 0.1f
        hudTable.y = Gdx.graphics.height * 0.90f
        hudTable.defaults().height(Gdx.graphics.height * 0.1f)
        hudTable.defaults().width(Gdx.graphics.width * 0.05f)

        hudTable.add(homeButton)
        hudTable.add(zoomInButton)
        hudTable.add(zoomOutButton)
        hudTable.add(slider)

        addActor(hudTable)

        homeButton.addClickListener { screen.homeClicked.onNext(Unit) }
        zoomInButton.addClickListener { screen.zoom.onNext(ArcheryInputProcessor.zoomDelta) }
        zoomOutButton.addClickListener { screen.zoom.onNext(-ArcheryInputProcessor.zoomDelta) }

        slider.addListener {
            screen.bowRotation.onNext(slider.value)
            true
        }
    }
}