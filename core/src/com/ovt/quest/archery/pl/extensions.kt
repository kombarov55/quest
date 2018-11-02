package com.ovt.quest.archery.pl

import com.badlogic.gdx.graphics.OrthographicCamera

fun OrthographicCamera.viewportBottomY(): Float {
    return position.y / zoom - viewportHeight / 2
}

fun OrthographicCamera.viewportTopY(): Float {
    return position.y / zoom + viewportHeight / 2
}

fun OrthographicCamera.viewportLeftX(): Float {
    return position.x / zoom - viewportWidth / 2
}

fun OrthographicCamera.viewportRightX(): Float {
    return position.x / zoom + viewportWidth / 2
}