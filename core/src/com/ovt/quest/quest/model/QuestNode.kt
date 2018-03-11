package com.ovt.quest.quest.model

/**
 * Created by nikolay on 05/01/2018.
 */

data class QuestNode(
        val id: String,
        val title: String?,
        val text: String,
        val background: String?,
        var options: List<Option>? = mutableListOf(),
        val events: QuestEvent? = null,
        var hidden: Boolean = false
)
