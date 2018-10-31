package com.ovt.quest.archery.box2d

import com.badlogic.gdx.physics.box2d.Body

/**
 * Получить userData из Body как HashMap. Если в userData лежит объект другого типа или его нет, то создастся новый
 * @param body box2d.Body
 * @return HashMap
 */
@Suppress("UNCHECKED_CAST")
fun Body.getDataMap(): MutableMap<String, String> {
    if (userData == null || userData !is Map<*, *>) {
        userData = mutableMapOf<String, String>()
    }

    return userData as MutableMap<String, String>
}