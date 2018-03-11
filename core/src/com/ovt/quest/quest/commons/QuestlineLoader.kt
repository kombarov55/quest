package com.ovt.quest.quest.commons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import com.badlogic.gdx.utils.XmlReader
import com.ovt.quest.quest.model.Option
import com.ovt.quest.quest.model.QuestEvent
import com.ovt.quest.quest.model.QuestNode


/**
 * Created by nikolay on 05/01/2018.
 */
object QuestlineLoader {

    private val reader = JsonReader()

    fun loadQuestNodes(): List<QuestNode> {
        val root = reader.parse(Gdx.files.internal("data/quest-nodes.json"))
        return root["questNodes"].map { node ->
            QuestNode(
                    id = node.getString("id"),
                    title = node.getNullableString("title"),
                    text = node.getString("text"),
                    options = mapOptions(node["options"]),
                    events = mapEvent(node["event"]))
        }
    }

    private fun mapOptions(jsonArray: JsonValue?): List<Option>? = jsonArray?.map { Option(it.getString("targetId"), it.getString("text")) }


    private fun mapEvent(jsonObj: JsonValue?): QuestEvent? =
            if (jsonObj == null)
                null else
                QuestEvent(jsonObj.getNullableString("setBackground"), jsonObj.getNullableString("openDiaryNote"), jsonObj.getNullableString("hideNote"))

    private fun JsonValue.getNullableString(name: String): String? {
        try {
            return this.getString(name)
        } catch (e: Exception) {
            return null
        }
    }
}
