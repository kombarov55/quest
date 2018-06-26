package com.ovt.quest.quest.commons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ovt.quest.quest.model.DiaryNote
import com.ovt.quest.quest.model.QuestNode

/**
 * Created by nikolay on 11/01/2018.
 */
class Globals {

    val questNodes: Map<String, QuestNode> = QuestlineLoader.loadQuestNodes().map { it.id to it }.toMap()
    val defaultQuestNode: QuestNode = questNodes.toList().first().second
    var currentQuestNode: QuestNode = defaultQuestNode
    val currentBg: Image  = Image(Textures.getTexture(currentQuestNode.background ?: defaultQuestNode.background!!))
    var allDiaryNotes: List<DiaryNote> = DiaryNotesLoader.load()

    var coins = 5

    fun reset() {
        coins = 5
    }

    init {
        currentBg.x = 0f
        currentBg.y = 0f
        currentBg.width = Gdx.graphics.width.toFloat()
        currentBg.height = Gdx.graphics.height.toFloat()
    }




}