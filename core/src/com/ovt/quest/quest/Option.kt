package com.ovt.quest.quest

/**
 * Created by nikolay on 07/01/2018.
 */

data class Option(
        val targetId: String? = "",
        val text: String = "",
        val action: String? = null,
        val openDiaryNote: String? = null,
        val startMinigame: String? = null,
        val hideOption: String? = null
)
