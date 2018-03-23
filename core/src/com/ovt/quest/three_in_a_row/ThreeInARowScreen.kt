package com.ovt.quest.three_in_a_row

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.ovt.quest.QuestGame
import com.ovt.quest.three_in_a_row.layout.ThreeInARowView
import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Items
import com.ovt.quest.three_in_a_row.model.Matrix

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowScreen(game: QuestGame) : Screen {

    private val maxRows = 10
    private val maxColumns = 10

    private val stage = ThreeInARowView(game)
    private val matrix = Matrix(maxColumns, maxRows)



    private val items = Items()

    override fun show() {
        addInitialItems()
        Gdx.input.inputProcessor = stage
        stage.onSwap = ::onSwap

        /*
        Начать обработку эвентов
        При свапе:
          проверить карту есть ли что нибудь в ряд:
            есть:
               сдвинуть вниз
               проверить и снести порядки
            пока не осталось больше упорядоченных

            нет:
             Свапнуть обратно
         */


        /*
        Что делает stage:
        Хранит матрицу и делает над ней операции
        Вызывает экшны у ячеек
        Обрабатывает нажатия, резолвит направления, определяет на что нажали
        Эмитит эвенты: сейчас только эвент после свапа

        Что делает Item:
        Хранит и рисует текстуру
        Хранит координаты x/y
        Хранит колонку и ряд
        Хранит свой тип
        Воспроизводит экшны

        Что делает матрица:
        Хранит в себе все Item-ы
        Получить, вставить Item
        Получить Item левее, правее, выше, ниже

        Как должно быть:
          Матрица (Логическое положение Item-ов):
            Хранить Item-ы
            Получить Item по колонке и ряду, получить Item левее и правее
            Отдавать колонку и ряд Item по его ссылке (так как при изменении координат объекта нужно это их менять так же в матрице)
          Item:
            Хранить свой тип
            Показать или изменить свою колонку и ряд через матрицу
            Хранить ItemActor:
              ItemActor:
                Хранить x/y
                Рисовать себя
                воспроизводить экшны
         View:
           Обрабатывать ввод
           Вызывать эвенты при определённом вводе
           Рендерить все Item (как подкласс State)
           Хранить и давать доступ к UI.
         Screen:
           Логика при эвентах


         */
    }

    private fun onSwap(i1LogicCoords: Pair<Int, Int>, i2LogicCoords: Pair<Int, Int>) {
        val (i1X, i1Y) = i1LogicCoords
        val (i2X, i2Y) = i2LogicCoords

        val i1 = matrix.get(i1X, i1Y)
        val i2 = matrix.get(i2X, i2Y)

        if (i1 != null && i2 != null) {
            i1.moveForSwap(i2X, i2Y)
            i2.moveForSwap(i1X, i1Y)
        }
    }

    private fun addInitialItems() {
        for (row in 0..9) {
            for (column in 0..9) {
                val i = items.rand(column, row)
                matrix.put(i, column, row)
            }
        }

        println(findMatches())
        matrix.forEach { stage.addActor(it) }
    }

    private fun findMatches(): MutableList<List<Item>> {
        val matches = mutableListOf<List<Item>>()
        for (row in 0 until maxRows) {
            for (column in 0 until maxColumns - 3) {
                val curr = matrix.get(column, row)!!
                val right1 = matrix.get(column + 1, row)!!
                val right2 = matrix.get(column + 2, row)!!
                if (curr.type == right1.type && curr.type == right2.type) {
                    matches.add(listOf(curr, right1, right2))
//                    matrix.put(items.rand(curr.column, curr.row))
//                    matrix.put(items.rand(right1.column, right1.row))
//                    matrix.put(items.rand(right2.column, right2.row))
                }
            }
        }

        return matches
    }



    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.input.inputProcessor = stage

        stage.act()
        stage.draw()
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
        items.allTextures.forEach { it.dispose() }
    }
}