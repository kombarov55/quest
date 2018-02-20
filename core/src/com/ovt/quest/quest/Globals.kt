package com.ovt.quest.quest

/**
 * Created by nikolay on 11/01/2018.
 */
class Globals {

    val questNodes: Map<String, QuestNode> = QuestlineLoader.loadQuestNodes()

    val defaultQuestNode: QuestNode = questNodes.toList().first().second

    var currentQuestNode: QuestNode = defaultQuestNode


}