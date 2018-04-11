package com.ovt.test.ThreeInARow

import com.ovt.quest.three_in_a_row.model.GroupFinder
import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Item.Type.*
import com.ovt.quest.three_in_a_row.model.Matrix
import java.nio.file.Files
import java.nio.file.Paths


fun main(args: Array<String>) {
    runTests()
}

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
    var required = listOf(
            listOf(m.get(1, 1), m.get(2, 1), m.get(3, 1), m.get(4, 1)),
            listOf(m.get(1, 3), m.get(2, 3), m.get(3, 3))

    )

    if (required.all { groups.contains(it) }) {
        println("second test: ok")
    } else {
        println("second test: fail")
        m.print()
        println("found groups: $groups")
        println("required groups: $required")
    }

    m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-group-finding-2")
    groups = GroupFinder.findGroups(m)
    val singleRequire = listOf(m.get(3, 1), m.get(4, 1), m.get(5, 1), m.get(3, 2), m.get(3, 3))
    if (groups.first().containsAll(singleRequire)) {
        println("third test: ok")
    } else {
        println("third test: fail")
        m.print()
        println("found groups: $groups")
        println("required groups: $singleRequire")
    }

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
                "R" -> Item(c, r, Red)
                "Y" -> Item(c, r, Yellow)
                "B" -> Item(c, r, Blue)
                "P" -> Item(c, r, Pink)
                "*" -> Item(c, r, Hole)
                else -> throw RuntimeException()
            }
            m.put(item)
        }
    }

    return m
}