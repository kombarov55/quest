package com.ovt.test.ThreeInARow

import com.ovt.quest.three_in_a_row.model.GroupFinder
import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Item.Type.*
import com.ovt.quest.three_in_a_row.model.Matrix
import io.kotlintest.matchers.beEmpty
import io.kotlintest.matchers.containsAll
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import java.nio.file.Files
import java.nio.file.Paths


class findSimpleGroup: StringSpec() {
    init {
        "should find all groups in matrix" {
            val m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-find-simple-group")
            val groups = GroupFinder.findGroups(m)
            groups.first() should containsAll(m.get(0, 1), m.get(1,1), m.get(2, 1))
        }
    }
}

class findTwoGroups: StringSpec() {
    init {
        val m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-two-groups")
        val groups = GroupFinder.findGroups(m)
        groups.size shouldBe 2
        groups[0] should containsAll (m.get(1, 1), m.get(2, 1), m.get(3, 1), m.get(4, 1))
        groups[1] should containsAll(m.get(1, 3), m.get(2, 3), m.get(3, 3))
    }
}

class findAngleGroups: StringSpec() {
    init {
        var m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-left-down-angle")
        var groups = GroupFinder.findGroups(m)
        groups.first() should containsAll(m.get(3, 1), m.get(4, 1), m.get(5, 1), m.get(3, 2), m.get(3, 3))

        m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-left-up-angle")
        groups = GroupFinder.findGroups(m)
        groups.first() should containsAll(m.get(3, 1), m.get(3, 2), m.get(3, 3), m.get(4, 3), m.get(5, 3))


        m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-right-up-angle")
        groups = GroupFinder.findGroups(m)
        groups.first() should containsAll(m.get(3, 3), m.get(4, 3), m.get(5, 3), m.get(5, 2), m.get(5, 1))

        m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-right-down-angle")
        groups = GroupFinder.findGroups(m)
        groups.first() should containsAll(m.get(3, 1), m.get(4, 1), m.get(5, 1), m.get(5, 2), m.get(5, 3))

        m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/stack-overflow-test")
        groups = GroupFinder.findGroups(m)
        groups.size shouldBe 0
    }
}

class testFindPlusGroup: StringSpec() {
    init {
        var m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-long-plus")
        var groups = GroupFinder.findGroups(m)
        groups.first() should containsAll(
                m.get(4, 1), m.get(4, 2), m.get(4, 3), m.get(4, 4), m.get(4, 5),
                m.get(3, 3), m.get(2, 3),
                m.get(5, 3), m.get(6, 3))

        m = loadMatrixFromFile("/Users/nikolay/IdeaProjects/quest/matrix-test-samples/test-long-plus")
        groups = GroupFinder.findGroups(m)
        groups.first() should containsAll(
                m.get(4, 3), m.get(4, 4), m.get(4, 5),
                m.get(3, 3), m.get(2, 3),
                m.get(5, 3), m.get(6, 3))
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