package com.ovt.quest.three_in_a_row.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.ovt.quest.QuestGame
import com.ovt.quest.quest.commons.Textures


class Hud(private val game: QuestGame): Stage() {

    val homeButton: ImageButton


    val redCounter: Label
    val blueCounter: Label
    val yellowCounter: Label
    val pinkCounter: Label

    val playerTotal: Label
    val playerImg: Image

    val enemyRedCounter: Label
    val enemyBlueCounter: Label
    val enemyYellowCounter: Label
    val enemyPinkCounter: Label

    val enemyTotal: Label
    val enemyImg: Image


    val redItem: Image
    val blueItem: Image
    val yellowItem: Image
    val pinkItem: Image

    val redItem2: Image
    val blueItem2: Image
    val yellowItem2: Image
    val pinkItem2: Image

    init {
        homeButton = game.buttons.imgButton(src = "img/home.png")

        redCounter = game.labelFactory.normalLabel("0")
        blueCounter = game.labelFactory.normalLabel("0")
        yellowCounter = game.labelFactory.normalLabel("0")
        pinkCounter = game.labelFactory.normalLabel("0")
        playerTotal = game.labelFactory.normalLabel("0/30")
        playerImg = Image(Textures.getTexture("player-portrait.png"))

        enemyRedCounter = game.labelFactory.normalLabel("0")
        enemyBlueCounter = game.labelFactory.normalLabel("0")
        enemyYellowCounter = game.labelFactory.normalLabel("0")
        enemyPinkCounter = game.labelFactory.normalLabel("0")
        enemyTotal = game.labelFactory.normalLabel("0/30")
        enemyImg = Image(Textures.getTexture("bandit-portrait.png"))

        redItem = Image(Textures.getTexture("item_red.png"))
        blueItem = Image(Textures.getTexture("item_blue.png"))
        yellowItem = Image(Textures.getTexture("item_yellow.png"))
        pinkItem = Image(Textures.getTexture("item_pink.png"))

        redItem2 = Image(Textures.getTexture("item_red.png"))
        blueItem2 = Image(Textures.getTexture("item_blue.png"))
        yellowItem2 = Image(Textures.getTexture("item_yellow.png"))
        pinkItem2 = Image(Textures.getTexture("item_pink.png"))

        val table = Table()

        table.setFillParent(true)

        table.defaults().expand()

        table.add(homeButton)
                .width(Gdx.graphics.width * 0.06f)
                .height(Gdx.graphics.width * 0.06f)
                .left()
                .top()
                .colspan(2)

        table.row()

        val playerScore = Table()

        playerScore.defaults()
                .width(Gdx.graphics.height * 0.04f)
                .height(Gdx.graphics.height * 0.04f)
                .pad(Gdx.graphics.width * 0.0005f)

        playerScore.add(playerImg).width(Gdx.graphics.height * 0.08f).height(Gdx.graphics.height * 0.08f)
        playerScore.add(redItem, redCounter, blueItem, blueCounter, yellowItem, yellowCounter, pinkItem, pinkCounter)
        playerScore.add(playerTotal).padLeft(Gdx.graphics.width * 0.02f)

        table.add(playerScore).left().bottom()

        val enemyScore = Table()

        enemyScore.defaults()
                .width(Gdx.graphics.height * 0.04f)
                .height(Gdx.graphics.height * 0.04f)
                .pad(Gdx.graphics.width * 0.0005f)

        enemyScore.add(enemyTotal).padRight(Gdx.graphics.width * 0.05f)
        enemyScore.add(redItem2, enemyRedCounter, blueItem2, enemyBlueCounter, yellowItem2, enemyYellowCounter, pinkItem2, enemyPinkCounter)
        enemyScore.add(enemyImg).width(Gdx.graphics.height * 0.08f).height(Gdx.graphics.height * 0.08f)

        table.add(enemyScore).right().bottom()

        addActor(table)
    }

}