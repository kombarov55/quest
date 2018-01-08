package com.ovt.quest.quest

/**
 * Created by nikolay on 05/01/2018.
 */

data class QuestNode(
        val id: String = "",
        val title: String? = "",
        val content: String = "",
        var options: MutableList<Option> = mutableListOf()
)
