package com.ovt.quest.quest.commons

import com.ovt.quest.QuestGame
import com.ovt.quest.quest.QuestScreen
import com.ovt.quest.quest.layout.PortraitsScreen
import com.ovt.quest.quest.layout.ScreamerScreen
import kotlin.reflect.full.memberFunctions

/**
 * Created by nikolay on 14/01/2018.
 */
class PseudoExecutor(private val game: QuestGame, private val screen: QuestScreen) {

    private val a = actions()

    fun executePseudoAction(id: String) {
        val name = id.substring(0, id.indexOf('('))
        val method = actions::class.memberFunctions.find { it.name == name }
        method?.call(a, id)
    }

    inner class actions {

        fun wine(signature: String) {
            val args = getArgs(signature)
            val successId = args[0]
            val failedId = args[1]

            if (game.globals.coins < 2) {
                screen.setCurrentNode(failedId)
            } else {
                game.globals.coins -= 2
                game.globals.currentQuestNode = game.globals.questNodes[successId]!!
                game.screen = ScreamerScreen(game)
            }
        }

        fun portraits(signature: String) {
            val targetId = getArgs(signature)[0]
            game.globals.currentQuestNode = game.globals.questNodes[targetId]!!
            game.screen = PortraitsScreen(game)
        }
    }

    private fun getArgs(signature: String): List<String> =
            signature.substring(signature.indexOf('(') + 1, signature.indexOf(')')).split(Regex(",\\s*"))
}