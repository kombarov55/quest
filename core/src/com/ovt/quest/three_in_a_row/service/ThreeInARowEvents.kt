package com.ovt.quest.three_in_a_row.service

import com.ovt.quest.three_in_a_row.ThreeInARowScreen
import com.ovt.quest.three_in_a_row.model.Coords
import com.ovt.quest.three_in_a_row.model.Item
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class ThreeInARowEvents(private val screen: ThreeInARowScreen) {

    private var totalPoints = 0
    private var redPoints = 0
    private var bluePoints = 0
    private var yellowPoints = 0
    private var pinkPoints = 0



    private val limit = 30

    val successfulSwap = PublishSubject.create<List<Item>>()

    val endPlayerTurn = PublishSubject.create<Unit>()


    val swap = PublishSubject.create<Pair<Coords, Coords>>()


    init {

        swap.flatMap { (c1, c2) ->
            screen.rxSwap(c1, c2)
        }.subscribe { unit -> println("complete!") }


        successfulSwap.subscribe { group ->

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

        endPlayerTurn.subscribe {
            screen.freeze()
            println("хмммм.....")
            Thread.sleep(1000)
            screen.onSwap(1 to 1, 2 to 1)
            println("Сходим вот так..")
            screen.unfreeze()

        }
    }

    fun swap() {

    }

}

fun main(args: Array<String>) {
    val o = Observable.just(1)
    o.flatMap {
        println("flatmap..")
        Thread.sleep(1000)
        println("flatmap complete")
        Observable.just(Unit)
    }.subscribe {
        println("complete")
    }
}