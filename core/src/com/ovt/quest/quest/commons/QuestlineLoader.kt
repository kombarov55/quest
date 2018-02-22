package com.ovt.quest.quest.commons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.XmlReader
import com.ovt.quest.quest.model.Option
import com.ovt.quest.quest.model.QuestNode


/**
 * Created by nikolay on 05/01/2018.
 */

object QuestlineLoader {

    fun loadQuestNodes(): Map<String, QuestNode> {

        val root = XmlReader().parse(Gdx.files.internal("data/quest-nodes.xml"))

        val questNodes = root.getChildrenByName("QuestNode").map { node ->

            val questNode = QuestNode(
                    id = node.getAttribute("id"),
                    title = node.getOptionAttribute("title"),
                    content = node.getAttribute("content"),
                    openDiaryNote = node.getOptionAttribute("openDiaryNote"),
                    action = node.getOptionAttribute("action")
            )

            val options = node.getChildByName("Options").getChildrenByName("Option").map { optionNode ->

                Option(
                        targetId = optionNode.getOptionAttribute("targetId"),
                        text = optionNode.getAttribute("text"),
                        action = optionNode.getOptionAttribute("action")
                )

            }



            questNode.options = options

            questNode.id to questNode
        }.toMap()

        return questNodes
    }

    private fun XmlReader.Element.getOptionAttribute(key: String): String? =
            attributes.find { it.key == key }?.value
}
