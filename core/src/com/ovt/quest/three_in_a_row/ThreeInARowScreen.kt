package com.ovt.quest.three_in_a_row

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ovt.quest.QuestGame
import com.ovt.quest.commons.addClickListener
import com.ovt.quest.horce_racing.layout.FinishTable
import com.ovt.quest.main_menu_screens.MainMenuScreen
import com.ovt.quest.three_in_a_row.layout.Hud
import com.ovt.quest.three_in_a_row.layout.ThreeInARowStage
import com.ovt.quest.three_in_a_row.model.*
import com.ovt.quest.three_in_a_row.model.Item.Type.Hole
import com.ovt.quest.three_in_a_row.service.GroupFinder
import com.ovt.quest.three_in_a_row.service.ItemFall
import com.ovt.quest.three_in_a_row.service.ThreeInARowEvents
import io.reactivex.Observable

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowScreen(private val game: QuestGame) : Screen {

    companion object {
        var maxRows = 5
        var maxColumns = 10
    }

    val matrix = RenderingMatrix(maxColumns, maxRows)
    private val stage = ThreeInARowStage(game, matrix)
    private val hud = Hud(game)
    private val itemFactory = ItemFactory(matrix)
    private val itemFall = ItemFall(matrix, itemFactory)
    private val events = ThreeInARowEvents(this)

    private val inputMultiplexer = InputMultiplexer(hud, stage)

    private val sound = Gdx.audio.newSound(Gdx.files.internal("sounds/shot-and-reload.wav"))


    override fun show() {
        matrix.fillWith(itemFactory)
        matrix.forEach { stage.addActor(it?.itemActor) }

        Gdx.input.inputProcessor = inputMultiplexer
        stage.onSwap = this::onSwap

        hud.homeButton.addClickListener {
            game.screen = MainMenuScreen(game)
        }

    }

    fun finish() {
        val finishTable = FinishTable(game)
        hud.addActor(finishTable)
    }

    fun onSwap(i1LogicCoords: Pair<Int, Int>, i2LogicCoords: Pair<Int, Int>) {

        events.swap.onNext(Coords(i1LogicCoords.first, i1LogicCoords.second) to Coords(i2LogicCoords.first, i2LogicCoords.second))

//        println("before swap: $i1LogicCoords and $i2LogicCoords")
//        matrix.print()
//
//        val i1 = matrix.get(i1LogicCoords)
//        val i2 = matrix.get(i2LogicCoords)
//
//        if (i1 == null || i2 == null) return
//        swap(i1, i2, then = {
//            val groups = GroupFinder.findGroups(matrix)
//            if (groups.isNotEmpty()) {
//                removeLoop(groups, matrix, itemFactory, stage)
//            } else {
//                swap(i1, i2)
//            }
//        })
    }

    fun rxVisualSwap(i1: Item, i2: Item): Observable<Pair<Item, Item>> {
        i1.itemActor!!.fastMoveTo(matrix.project(i2))
        return i2.itemActor!!.RX_fastMoveTo(matrix.project(i1))
                .map { i1 to i2 }
    }

    fun coordsSwap(i1: Item, i2: Item) {
        val (i1col, i1row) = i1.column to i1.row

        i1.setLogicCoords(i2.column, i2.row)
        matrix.put(i1)

        i2.setLogicCoords(i1col, i1row)
        matrix.put(i2)
    }


    fun getItems(c1: Coords, c2: Coords): Pair<Item?, Item?> = matrix.get(c1) to matrix.get(c2)


    fun updateCounters(red: Int, blue: Int, yellow: Int, pink: Int) {
        hud.redCounter.setText(red.toString())
        hud.blueCounter.setText(blue.toString())
        hud.yellowCounter.setText(yellow.toString())
        hud.pinkCounter.setText(pink.toString())
        hud.playerTotal.setText("" + (red + blue + yellow + pink) + "/30")
    }

    fun freeze() {
        inputMultiplexer.removeProcessor(stage)
    }

    fun unfreeze() {
        inputMultiplexer.addProcessor(stage)
    }

    private fun removeLoop(matches: List<List<Item>>, matrix: RenderingMatrix, itemFactory: ItemFactory, stage: Stage) {
        removeGroups(matches, then = {
            events.successfulSwap.onNext(matches.flatten())
            itemFall.executeFallDown(matrix, itemFactory, then = {
                addNewItems(matrix, itemFactory, stage, then = {
                    val newGroups = GroupFinder.findGroups(matrix)
                    if (newGroups.isNotEmpty()) {
                        removeLoop(newGroups, matrix, itemFactory, stage)
                    } else {
                        events.endPlayerTurn.onNext(Unit)
                    }
                })
            })
        })
    }



    private fun removeGroups(matches: List<List<Item>>, then: () -> Unit = { println("After match remove") }) {
        if (matches.isEmpty()) return
        val flattened = matches.flatten()

        flattened.dropLast(1).forEach {
            matrix.put(itemFactory.hole(it.column, it.row))
            it.itemActor?.dissapear(then = {
                it.remove()
            })
        }

        flattened.last().let { first ->
            matrix.put(itemFactory.hole(first.column, first.row))
            first.itemActor?.dissapear(then = {
                first.remove()
                then.invoke()
            })
        }

        sound.play(0.4f)
    }

    fun RX_visualRemove(groups: List<Item>): Observable<List<Item>> {
        groups.dropLast(1).forEach { it.itemActor?.dissapear() }
        return groups.last().itemActor!!.RX_dissapear().map { groups }

    }

    fun coordsRemove(group: List<Item>) {
        group.forEach { matrix.put(itemFactory.hole(it.column, it.row)) }
    }

    fun RX_fallDown(): Observable<Unit> {
        return Observable.create { s ->
            itemFall.executeFallDown(matrix, itemFactory, {
                s.onNext(Unit)
                s.onComplete()
            })
        }
    }




    fun swap(i1: Pair<Int, Int>, i2: Pair<Int, Int>, then: () -> Unit = { println("After swap!") }) {
        swap(matrix.get(i1)!!, matrix.get(i2)!!, then)
    }

    private fun swap(i1: Item, i2: Item, then: () -> Unit = { println("After swap!") }) {
        val (i1col, i1row) = i1.column to i1.row

        i1.setLogicCoords(i2.column, i2.row)
        matrix.put(i1)
        i1.itemActor?.fastMoveTo(matrix.project(i2.column, i2.row))

        i2.setLogicCoords(i1col, i1row)
        i2.itemActor?.fastMoveTo(matrix.project(i1col, i1row), then)
        matrix.put(i2)

    }

    private fun addNewItems(matrix: Matrix, itemFactory: ItemFactory, stage: Stage, then: () -> Unit = {  }) {
        val holes = matrix.flatten().filter { it?.type == Hole }

        if (holes.isNotEmpty()) {
            holes.take(holes.size - 1).forEach { item ->
                val ii = itemFactory.nonMatchingItem(item!!.column, item!!.row, matrix)
                matrix.put(ii)
                stage.addActor(ii.itemActor)
                ii.itemActor?.comeOut()
            }

            holes.last().let { item ->
                val ii = itemFactory.nonMatchingItem(item!!.column, item!!.row, matrix)
                matrix.put(ii)
                stage.addActor(ii.itemActor)
                ii.itemActor?.comeOut(then = then)
            }
        }
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act()
        stage.draw()

        hud.act()
        hud.draw()
    }

    override fun hide() {
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {
        itemFactory.allTextures.forEach { it.dispose() }
        sound.dispose()
    }
}