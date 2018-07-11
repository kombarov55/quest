package com.ovt.quest.three_in_a_row.service

import com.ovt.quest.three_in_a_row.ThreeInARowScreen
import com.ovt.quest.three_in_a_row.model.Coords
import com.ovt.quest.three_in_a_row.model.Item
import io.reactivex.subjects.PublishSubject


class ThreeInARowEvents(private val screen: ThreeInARowScreen) {

    private var totalPoints = 0
    private var redPoints = 0
    private var bluePoints = 0
    private var yellowPoints = 0
    private var pinkPoints = 0

    private var enemyPoints = 0
    private var enemyRedPoints = 0
    private var enemyBluePoints = 0
    private var enemyYellowPoints = 0
    private var enemyPinkPoints = 0




    private val limit = 30

    val startEnemyTurn = PublishSubject.create<Unit>()

    val swap = PublishSubject.create<Pair<Coords, Coords>>()
    val removeLoop = PublishSubject.create<List<Item>>()
    val playerScored = PublishSubject.create<List<Item>>()


    val enemySwap = PublishSubject.create<Pair<Item, Item>>()
    val enemyRemoveLoop = PublishSubject.create<List<Item>>()
    val enemyScored = PublishSubject.create<List<Item>>()

    val swapBack = PublishSubject.create<Pair<Item, Item>>()

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
                    playerScored.onNext(group)
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

        playerScored.subscribe { group ->
            totalPoints += group.size
            if (totalPoints >= limit) {
                screen.win()
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
            val turn  = Prediction.findAvailableTurns(screen.matrix)
            enemySwap.onNext(turn.first().toSwap)
            println("Сходим вот так..")

        }

        enemySwap
                .flatMap { (i1, i2) -> screen.rxVisualSwap(i1, i2) }
                .doOnNext { (i1, i2) -> screen.coordsSwap(i1, i2) }
                .subscribe { pair ->
                    val groups = GroupFinder.findGroups(screen.matrix)
                    if (groups.isEmpty()) {
                        swapBack.onNext(pair)
                    } else {
                        enemyRemoveLoop.onNext(groups.flatten())
                    }
                }

        enemyRemoveLoop
                .doOnNext { group ->
                    screen.playDissapearSound()
                    enemyScored.onNext(group)
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
                        enemyRemoveLoop.onNext(groups.flatten())
                    } else {
                        screen.unfreeze()
                    }
                }

        enemyScored.subscribe { group ->
            enemyPoints += group.size
            if (enemyPoints >= limit) {
                screen.lose()
            }

            group.forEach { item ->
                when (item.type) {
                    Item.Type.Red -> enemyRedPoints += 1
                    Item.Type.Blue -> enemyBluePoints += 1
                    Item.Type.Yellow -> enemyYellowPoints += 1
                    Item.Type.Pink -> enemyPinkPoints += 1
                }
            }
            screen.updateEnemyCounters(enemyRedPoints, enemyBluePoints, enemyYellowPoints, enemyPinkPoints)
        }
    }

}