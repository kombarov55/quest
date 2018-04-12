package com.ovt.test.ThreeInARow

import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Item.Type.*
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class neighbourTest: StringSpec() {
    init {
        "should assert neighors" {
            Item(5, 5, Hole).isNeighbourTo(Item(4, 5, Hole)) shouldBe true
            Item(5, 5, Hole).isNeighbourTo(Item(6, 5, Hole)) shouldBe true
            Item(5, 5, Hole).isNeighbourTo(Item(5, 4, Hole)) shouldBe true
            Item(5, 5, Hole).isNeighbourTo(Item(4, 5, Hole)) shouldBe true

            Item(5, 5, Hole).isNeighbourTo(Item(5, 7, Hole)) shouldBe false
            Item(5, 5, Hole).isNeighbourTo(Item(6, 6, Hole)) shouldBe false
        }
    }
}