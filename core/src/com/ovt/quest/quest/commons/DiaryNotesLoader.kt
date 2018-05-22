package com.ovt.quest.quest.commons

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.XmlReader
import com.ovt.quest.quest.model.DiaryNote

object DiaryNotesLoader {


    fun load(): List<DiaryNote> {
        val reader = XmlReader()
        val root = reader.parse(Gdx.files.internal("data/diary-notes.xml"))
        return root.getChildrenByName("DiaryNote")
                .map { it ->
                    DiaryNote(
                            ordinal = it.attributes["ordinal"].toInt(),
                            title = it.attributes["title"],
                            content = it.attributes["content"])
                }
    }

}