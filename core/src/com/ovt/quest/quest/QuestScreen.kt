package com.ovt.quest.quest

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.ovt.quest.QuestGame
import com.ovt.quest.quest.commons.PseudoExecutor
import com.ovt.quest.quest.model.Option
import com.ovt.quest.quest.layout.QuestStage
import com.ovt.quest.quest.layout.ScreamerScreen
import com.ovt.quest.quest.model.QuestEvent
import com.ovt.quest.quest.model.QuestNode

/**
 * Created by kombarov_na on 26.12.2017.
 */

class QuestScreen(private val game: QuestGame) : ScreenAdapter() {
    private val questStage = QuestStage(game)
    private val pseudoExecutor = PseudoExecutor(game, this)

    override fun show() {
        Gdx.input.inputProcessor = questStage
        Gdx.input.isCatchBackKey = true
        questStage.onOptionClickListener = onOptionClicked
        displayNode(game.globals.currentQuestNode)
    }

    fun setCurrentNode(id: String) {
        val node = game.globals.questNodes[id]!!
        game.globals.currentQuestNode = node

        executeEvents(node.events)

        displayNode(node)
    }

    fun notifyDiaryNote(noteTitle: String) {
        questStage.notifyDiaryNote(noteTitle)
    }

    private fun displayNode(node: QuestNode) {
        if (node.background != null) questStage.setBackground(node.background)

        questStage.titleLabel.setText(node.title)
        questStage.contentLabel.setText(node.text)

        questStage.clearOptions()
        questStage.addOptions(node.options?.filter { nonHidden(it) }?.map { it.text })

    }

    private fun nonHidden(option: Option): Boolean  {
        return if (option.targetId.endsWith('+'))
            true else
            game.globals.questNodes[option.targetId]?.hidden == false
    }


    val onOptionClicked = { optionText: String ->
        val selectedOption = game.globals.currentQuestNode.options!!.find { it.text == optionText }!!
        val nextNodeId = selectedOption.targetId
        if (nextNodeId.endsWith("+")) {
            pseudoExecutor.executePseudoAction(nextNodeId)
        } else {
            val nextNode = game.globals.questNodes[nextNodeId]!!
            game.globals.currentQuestNode = nextNode

            executeEvents(nextNode.events)

            displayNode(nextNode)
        }
    }

    private fun executeEvents(events: QuestEvent?) {
        if (events == null) return
        if (events.hideNoteId != null) game.globals.questNodes[events.hideNoteId]?.hidden = true
        if (events.diaryNoteId != null) {
            questStage.notifyDiaryNote(events.diaryNoteId)
        }
        if (events.screamer == true) {
            game.screen = ScreamerScreen(game)
        }
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        questStage.act()
        questStage.draw()
    }
}
