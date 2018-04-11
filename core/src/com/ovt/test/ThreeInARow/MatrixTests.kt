package com.ovt.test.ThreeInARow

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.ovt.quest.three_in_a_row.Vector2
import com.ovt.quest.three_in_a_row.model.GroupFinder
import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Matrix
import java.nio.file.Files
import java.nio.file.Paths


fun runTests() {
    testFindingGroups()
}

fun testFindingGroups() {
    var m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-group-finding-1")
    var groups = GroupFinder.findGroups(m)
    if (groups.contains(listOf(m.get(0, 1), m.get(1,1), m.get(2, 1)))) {
        println("first test: ok")
    } else {
        println("first test: fail")
    }

    m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-group-finding-2")
    groups = GroupFinder.findGroups(m)
    val matches = listOf(
            listOf(m.get(1, 1), m.get(2, 1), m.get(3, 1), m.get(4, 1)),
            listOf(m.get(1, 3), m.get(2, 3), m.get(3, 3))

    )

    if (matches.all { groups.contains(it) }) {
        println("second test: ok")
    } else {
        println("second test: fail")
        m.print()
        println("found groups: $groups")
        println("required groups: $matches")
    }

    println(groups)
}

fun loadMatrixFromFile(path: String): Matrix {

    val lines = Files.readAllLines(Paths.get(path))
            .reversed()
            .map { line -> line.split(" ") }

    val width = lines[0].size
    val height = lines.size

    val m = Matrix(width, height)

    for (r in 0 until height) {
        for (c in 0 until width) {

            val item = when (lines[r][c]) {
                "R" -> Item(c, r, Vector2(0, 0), 0f, 0f, Texture(Gdx.files.internal("img/sasha.jpg")), Item.Type.Red)
                "Y" -> Item(c, r, Vector2(0, 0), 0f, 0f, Texture(Gdx.files.internal("img/sasha.jpg")), Item.Type.Yellow)
                "B" -> Item(c, r, Vector2(0, 0), 0f, 0f, Texture(Gdx.files.internal("img/sasha.jpg")), Item.Type.Blue)
                "P" -> Item(c, r, Vector2(0, 0), 0f, 0f, Texture(Gdx.files.internal("img/sasha.jpg")), Item.Type.Pink)
                "*" -> Item(c, r, Vector2(0, 0), 0f, 0f, Texture(Gdx.files.internal("img/sasha.jpg")), Item.Type.Hole)
                else -> throw RuntimeException()
            }
            m.put(item)
        }
    }

    return m
}