package com.ovt.quest.quest.commons

import com.ovt.quest.commons.QuestGame

/**
 * Created by nikolay on 14/01/2018.
 */
object
QuestActions {

    val actions: Map<String, (QuestGame) -> String?> = mapOf(
            "hide2" to {game: QuestGame ->
                game.globals.questNodes["2"]?.hidden = true
                null
            },

            "hide3" to {game: QuestGame ->
                game.globals.questNodes["3"]?.hidden = true
                null
            },

            "hide4" to {game: QuestGame ->
                game.globals.questNodes["4"]?.hidden = true
                null
            },

            "hide5" to {game: QuestGame ->
                game.globals.questNodes["5"]?.hidden = true
                null
            },

            "hide6" to {game: QuestGame ->
                game.globals.questNodes["6"]?.hidden = true
                null
            }

    )

}