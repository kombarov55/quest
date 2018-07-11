package com.ovt.quest.archery

import com.badlogic.gdx.math.Vector2
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

object ArcheryEvents {

    val moveCamera = PublishSubject.create<Vector2>()
    val zoomCamera = PublishSubject.create<Float>()
    val goHome = PublishSubject.create<Unit>()
    val bowPower = PublishSubject.create<Float>()
    val bowRotation = PublishSubject.create<Float>()
    val fireBow = PublishSubject.create<Pair<Float, Float>>()
    val touch = PublishSubject.create<Vector2>()
    val createArrow = PublishSubject.create<Unit>()

    val animation = Observable.interval(200, TimeUnit.MILLISECONDS)



}