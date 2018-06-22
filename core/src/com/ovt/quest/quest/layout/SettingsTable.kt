package com.ovt.quest.quest.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ovt.quest.QuestGame
import com.ovt.quest.quest.commons.UIEvents


class SettingsTable(game: QuestGame): Table() {

    private val BUTTON_SIDE_SIZE = Gdx.graphics.width * 0.05f

    private var toggled = false

    val settingsButton = game.buttons.imgButton("img/settings.png", onClick = {
        toggled = !toggled
        UIEvents.toggleSettings.onNext(toggled)
    })
    val diaryButton = game.buttons.imgButton("img/diary.png", onClick = { UIEvents.toggleDiary.onNext(true) })
    val homeButton = game.buttons.imgButton("img/home.png", onClick = { UIEvents.homePressed.onNext(Unit) })

    init {
        diaryButton.isVisible = false
        homeButton.isVisible = false

        defaults().width(BUTTON_SIDE_SIZE).height(BUTTON_SIDE_SIZE).expandY().fillY()
        add(settingsButton)
        add(diaryButton)
        add(homeButton)

        subscribeToggleSettings()
    }

    private fun subscribeToggleSettings() {
        UIEvents.toggleSettings.subscribe { show ->

            if (show) {
                diaryButton.isVisible = true
                homeButton.isVisible = true
            } else {
                diaryButton.isVisible = false
                homeButton.isVisible = false
            }
        }
    }
}