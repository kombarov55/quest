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

    private val bowRotationSlider: Slider = Slider(0f, 90f, 1f, true, game.skin)
    private val bowPowerSlider = Slider(0f, 1f, 0.01f, true, game.skin)
    private val fireButton = game.buttons.imgButton("img/explosion.png")


    private val buttonsTable = Table()

    private val bowControlTable = Table()


    init {
        buttonsTable.x = Gdx.graphics.width * 0.1f
        buttonsTable.y = Gdx.graphics.height * 0.90f
        buttonsTable.defaults().height(Gdx.graphics.height * 0.1f)
        buttonsTable.defaults().width(Gdx.graphics.width * 0.05f)

        buttonsTable.add(homeButton)
        buttonsTable.add(zoomInButton)
        buttonsTable.add(zoomOutButton)

        addActor(buttonsTable)

        bowControlTable.x = Gdx.graphics.width * 0.1f
        bowControlTable.y = Gdx.graphics.height * 0.7f
        bowControlTable.add(bowRotationSlider)
        bowControlTable.add(bowPowerSlider).padLeft(25f)
        bowControlTable.add(fireButton).padLeft(25f).width(50f).height(50f)

        addActor(bowControlTable)


        homeButton.addClickListener { screen.homeClicked.onNext(Unit) }
        zoomInButton.addClickListener { screen.zoom.onNext(ArcheryInputProcessor.zoomDelta) }
        zoomOutButton.addClickListener { screen.zoom.onNext(-ArcheryInputProcessor.zoomDelta) }

        bowRotationSlider.addListener {
            screen.bowRotation.onNext(bowRotationSlider.value)
            true
        }

        fireButton.addClickListener { screen.bowFired.onNext(Unit) }
    }
}