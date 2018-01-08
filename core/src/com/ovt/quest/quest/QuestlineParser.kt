package com.ovt.quest.quest

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.XmlReader
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler


/**
 * Created by nikolay on 05/01/2018.
 */

object QuestlineParser: DefaultHandler() {

    private var result: MutableList<QuestNode> = mutableListOf()
    private var currentNode = QuestNode()

    fun loadQuestNodes(): List<QuestNode> {

        val xmlReader = XmlReader()
        val root = xmlReader.parse(Gdx.files.internal("quest-nodes.xml"))

        val questNodes = root.getChildrenByName("QuestNode").map { node ->
            val questNode = QuestNode(
                    id = node.getAttribute("id"),
                    title = node.attributes.find { it.key == "title" }?.value,
                    content = node.getAttribute("content")
            )

            val options = node.getChildByName("options").getChildrenByName("option").map { optionNode ->

                Option(
                        targetId = optionNode.getAttribute("targetId"),
                        text = optionNode.getAttribute("text"),
                        action = optionNode.attributes.find { it.key == "action" }?.value
                )

            }

            questNode.options.addAll(options)

            questNode
        }

        return questNodes











//
//        result.clear()
//
//        val factory = SAXParserFactory.newInstance()
//        val saxParser = factory.newSAXParser()
//
//        saxParser.parse(Gdx.files.internal("quest-nodes.xml").file(), this)
//
//        return result
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        val attrs = attributes!!

        if (qName == "QuestNode") {

            currentNode = QuestNode(
                    id = attrs.getValue("id").orEmpty(),
                    title = attrs.getValue("title").orEmpty(),
                    content = attrs.getValue("content").orEmpty()
            )

            result.add(currentNode)

        } else if (qName == "option") {

            val option = Option(
                    targetId = attrs.getValue("targetId").orEmpty(),
                    text = attrs.getValue("text").orEmpty(),
                    action = attrs.getValue("action")
            )

            currentNode.options.add(option)

        }
    }
}
