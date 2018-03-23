package com.ovt.quest.three_in_a_row

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.ovt.quest.QuestGame
import com.ovt.quest.three_in_a_row.layout.ThreeInARowView
import com.ovt.quest.three_in_a_row.model.Item
import com.ovt.quest.three_in_a_row.model.Items

/**
 * Created by nikolay on 14.03.18.
 */
class ThreeInARowScreen(game: QuestGame) : Screen {

    private val stage = ThreeInARowView(game)

    private val items = Items(10, 10)

    override fun show() {
        Gdx.input.inputProcessor = stage
        initScreen()
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

    private fun onSwap(i1: Item, i2: Item) {

    }

    private fun initScreen() {
        for (row in 0..9) {
            for (column in 0..9) {
                val i = items.rand(column, row)
                stage.addActor(i)
                i.comeOut()
            }
        }
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