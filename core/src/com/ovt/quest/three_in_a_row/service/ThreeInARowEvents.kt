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

    val startEnemyTurn = PublishSubject.create<Unit>()


    val swap = PublishSubject.create<Pair<Coords, Coords>>()
    val swapBack = PublishSubject.create<Pair<Item, Item>>()

    val removeLoop = PublishSubject.create<List<Item>>()


    init {
        swap
                .doOnNext { screen.freeze() }
                .map { (c1, c2) ->  screen.getItems(c1, c2) }
                .filter { (c1, c2) -> c1 != null && c2 != null }.map { (c1, c2) -> c1!! to c2!! }
                .flatMap { (i1, i2) -> screen.rxVisualSwap(i1, i2) }
                .doOnNext { (i1, i2) -> screen.coordsSwap(i1, i2) }
                .subscribe { pair ->
                    val groups = GroupFinder.findGroups(screen.matrix)
                    if (groups.isEmpty()) {
                        swapBack.onNext(pair)
                    } else {
                        removeLoop.onNext(groups.flatten())
                    }
                }

        swapBack
                .flatMap { (i1, i2) -> screen.rxVisualSwap(i1, i2) }
                .subscribe { (i1, i2) ->
                    screen.coordsSwap(i1, i2)
                    screen.unfreeze()
                }


        removeLoop
                .doOnNext { group ->
                    screen.playDissapearSound()
                    successfulSwap.onNext(group)
                }
                .flatMap { groups -> screen.RX_visualRemove(groups) }
                .doOnNext {group -> screen.coordsRemove(group) }
                .flatMap { screen.RX_fallDown() }
                .map { screen.findHoles() }
                .map { holes -> screen.holesToNewItems(holes) }
                .flatMap { items -> screen.RX_fillHolesVisually(items) }
                .doOnNext { items -> screen.fillHolesInMatrix(items) }
                .map { GroupFinder.findGroups(screen.matrix) }
                .subscribe { groups ->
                    if (groups.isNotEmpty()) {
                        removeLoop.onNext(groups.flatten())
                    } else {
                        startEnemyTurn.onNext(Unit)
                    }
                }



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

        startEnemyTurn.subscribe {
            println("хмммм.....")
            Thread.sleep(1000)
            swap.onNext(Coords(1, 1) to Coords(2, 1))
            println("Сходим вот так..")

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