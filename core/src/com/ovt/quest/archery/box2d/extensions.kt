package com.ovt.quest.archery.box2d

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Fixture

/**
 * Получить userData из первой Fixture как HashMap. Если в userData
 * лежит объект другого типа или его нет, то создастся новый.
 *
 * @return HashMap
 */
@Suppress("UNCHECKED_CAST")
fun Body.getFixtureUserData(): MutableMap<String, String> {
    if (fixtureList.first().userData == null || fixtureList.first().userData !is Map<*, *>) {
        fixtureList.first().userData = mutableMapOf<String, String>()
    }

    return fixtureList.first().userData as MutableMap<String, String>
}

/**
 * Получить userData, который хранится как MutableMap. Предполагается что
 * userData была заполнена методом getFixtureUserData
 *
 * @return HashMap
 */
@Suppress("UNCHECKED_CAST")
fun Fixture.getUserDataMap(): MutableMap<String, String> {
    if (userData == null) {
        userData = mutableMapOf<String, String>()
    }
    return userData as MutableMap<String, String>
}