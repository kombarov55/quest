package com.ovt.quest.three_in_a_row.service

import com.ovt.quest.three_in_a_row.ThreeInARowScreen
import com.ovt.quest.three_in_a_row.model.Item
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


class ThreeInARowEvents(private val screen: ThreeInARowScreen) {

    private var totalPoints = 0
    private var redPoints = 0
    private var bluePoints = 0
    private var yellowPoints = 0
    private var pinkPoints = 0



    private val limit = 30

    val swapped = PublishSubject.create<List<Item>>()
    val endPlayerTurn = PublishSubject.create<Unit>()
    val endEnemyTurn = PublishSubject.create<Unit>()

    init {
        swapped.subscribe { group ->

            totalPoints += group.size
            if (totalPoints >= limit) {
                screen.finish()
            }

            group.forEach { item ->
                when (item.type) {
                    Item.Type.Red -> redPoints += 1
                    Item.Type.Blue -> bluePoints += 1
                    Item.Type.Yellow -> yellowPoints += 1
                    Item.Type.Pink -> pinkPoints += 1
                }
            }

            screen.updateCounters(redPoints, bluePoints, yellowPoints, pinkPoints)
        }

        endPlayerTurn.subscribeOn(Schedulers.newThread()).subscribe {
//            screen.freeze()
        }

        endEnemyTurn.subscribeOn(Schedulers.newThread()).subscribe {
//            screen.unfreeze()
        }
    }


}