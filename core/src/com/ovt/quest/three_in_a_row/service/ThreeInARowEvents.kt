package com.ovt.quest.three_in_a_row.service

import com.ovt.quest.three_in_a_row.ThreeInARowScreen
import com.ovt.quest.three_in_a_row.model.Item
import io.reactivex.subjects.PublishSubject


class ThreeInARowEvents(private val screen: ThreeInARowScreen) {

    private var points = 0

    private val limit = 30

    val swapped = PublishSubject.create<List<Item>>()

    init {
        swapped.subscribe { group ->
            points += group.size
        }

        if (points >= limit) {
            screen.finish()
        }
    }


}