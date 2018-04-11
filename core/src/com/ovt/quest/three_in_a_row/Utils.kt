package com.ovt.quest.three_in_a_row

import com.badlogic.gdx.math.Vector2

/**
 * Created by nikolay on 23/03/2018.
 */
fun toPositive(x: Int) = if (x < 0) -x else x
fun toPositive(x: Float) = if (x < 0) -x else x

fun Vector2(x: Int, y: Int): Vector2 = Vector2(x.toFloat(), y.toFloat())