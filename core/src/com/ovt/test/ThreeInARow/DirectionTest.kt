package com.ovt.test.ThreeInARow

import com.ovt.quest.three_in_a_row.Direction
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class testOpposite: StringSpec() {
    init {
        Direction.Up.opposite() shouldBe Direction.Down
        Direction.Right.opposite() shouldBe Direction.Left
        Direction.Down.opposite() shouldBe Direction.Up
        Direction.Left.opposite() shouldBe Direction.Right
    }
}