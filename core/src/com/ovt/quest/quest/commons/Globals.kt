package com.ovt.quest.quest.commons

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
    var currentBg: Image  = Image(R.getTexture(currentQuestNode.background ?: defaultQuestNode.background!!))
    var allDiaryNotes: List<DiaryNote> = DiaryNotesLoader.load()




}