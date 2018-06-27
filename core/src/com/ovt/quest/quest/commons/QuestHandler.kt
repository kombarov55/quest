package com.ovt.quest.quest.commons

import com.ovt.quest.QuestGame
import com.ovt.quest.bordel.BordelScreen
import com.ovt.quest.horce_racing.HorceRacingScreen
import com.ovt.quest.quest.QuestScreen
import com.ovt.quest.quest.layout.PortraitsScreen
import com.ovt.quest.quest.layout.ScreamerScreen
import com.ovt.quest.quest.model.QuestEvent

/**
 * Created by nikolay on 14/01/2018.
 */
class QuestHandler(private val game: QuestGame, private val screen: QuestScreen) {

    fun handle(optionText: String) {
        val selectedOption = game.globals.currentQuestNode.options!!.find { it.text == optionText }!!
        val nextNodeId = selectedOption.targetId
        if (nextNodeId.endsWith("+")) {
            executePseudoAction(nextNodeId)
        } else {
            val nextNode = game.globals.questNodes[nextNodeId]!!
            game.globals.currentQuestNode = nextNode

            executeEvents(nextNode.events)

            screen.displayNode(nextNode)
        }
    }

    private fun executeEvents(events: QuestEvent?) {
        if (events == null) return
        if (events.hideNoteId != null) game.globals.questNodes[events.hideNoteId]?.hidden = true
        if (events.diaryNoteId != null) {
            screen.notifyDiaryNote(events.diaryNoteId)
        }
        if (events.screamer == true) {
            game.screen = ScreamerScreen(game)
        }
    }

    fun executePseudoAction(id: String) {
        val name = id.substring(0, id.indexOf('('))
        val method = actions[name]!!
        method.invoke(id)
    }
    
    val actions = mapOf(
            "wine" to { signature: String ->
                val args = getArgs(signature)
                val successId = args[0]
                val failedId = args[1]

                if (game.globals.coins < 2) {
                    setCurrentNode(failedId)
                } else {
                    game.globals.coins -= 2
                    game.globals.currentQuestNode = game.globals.questNodes[successId]!!
                    game.screen = ScreamerScreen(game)
                }
            },
            "portraits" to { signature: String ->
                game.screen = PortraitsScreen(game)
            },
            "horceRacing" to { _: String ->
                game.screen = HorceRacingScreen(game)
            },
            "bordel" to { signature: String ->
                val nextId = getArgs(signature)[0]
                game.globals.currentQuestNode = game.globals.questNodes[nextId]!!
                game.screen = BordelScreen(game)
            }
    )

    fun setCurrentNode(id: String) {
        val node = game.globals.questNodes[id]!!
        game.globals.currentQuestNode = node

        executeEvents(node.events)

        screen.displayNode(node)
    }

    private fun getArgs(signature: String): List<String> =
            signature.substring(signature.indexOf('(') + 1, signature.indexOf(')')).split(Regex(",\\s*"))
}