package com.ovt.test.ThreeInARow

import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Item.Type.Blue
import com.ovt.quest.three_in_a_row.model.Item.Type.Red
import com.ovt.quest.three_in_a_row.service.Prediction
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

val group = listOf(Item(0, 0, Red), Item(1, 0, Blue), Item(2, 0, Red))

class testCountByColors: StringSpec() {
    init {
        val map = Prediction.countByColors(group)
        println(map)
    }
}

class testIsIncompleteGroup: StringSpec() {
    init {
        val result = Prediction.isIncompleteGroup(Prediction.countByColors(group))
        println(result)
        result shouldBe true
    }
}

class predict1: StringSpec() {
    init {
        val m = loadMatrixFromFile(matrixDir + "predict-1")
        println(Prediction.findAvailableTurns(m))
    }
}

