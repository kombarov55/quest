package com.ovt.test.ThreeInARow

import com.ovt.quest.commons.Direction
import com.ovt.quest.commons.Direction.*
import io.kotlintest.matchers.contain
import io.kotlintest.matchers.containsAll
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class testOpposite: StringSpec() {
    init {
        Up.opposite() shouldBe Down
        Right.opposite() shouldBe Left
        Down.opposite() shouldBe Up
        Left.opposite() shouldBe Right
    }
}

class testNormal: StringSpec() {
    init {
        Up.normal() should containsAll(Left, Right)
        Down.normal() should containsAll(Left, Right)
        Left.normal() should containsAll(Up, Down)
        Right.normal() should containsAll(Up, Down)
    }
}