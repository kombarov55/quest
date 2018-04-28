package com.ovt.quest.archery

import com.badlogic.gdx.math.Vector2
import io.reactivex.subjects.PublishSubject

object Events {

    val moveCamera = PublishSubject.create<Vector2>()
    val zoomCamera = PublishSubject.create<Float>()
    val goHome = PublishSubject.create<Unit>()
    val rotateBow = PublishSubject.create<Float>()
    val touch = PublishSubject.create<Vector2>()


}