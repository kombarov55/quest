package com.ovt.quest.quest.commons

import com.ovt.quest.QuestGame
import kotlin.reflect.full.memberFunctions

/**
 * Created by nikolay on 14/01/2018.
 */
class QuestActions(private val game: QuestGame) {

    private val a = actions()

    fun nextIdWithCond(id: String): String {
        val name = id.substring(0, id.indexOf('('))
        val method = actions::class.memberFunctions.find { it.name == name }
        return method?.call(a, id) as String
    }

    inner class actions {

        fun wine(signature: String): String {
            val args = getArgs(signature)
            val failedId = args[0]
            val successId = args[1]

            game.globals.coins
            if (game.globals.coins < 2) {
                return failedId
            } else {
                game.globals.coins -= 2
                return successId
            }
        }


    }

    private fun getArgs(signature: String): List<String> =
            signature.substring(signature.indexOf('(') + 1, signature.indexOf(')')).split(Regex(",\\s*"))
}