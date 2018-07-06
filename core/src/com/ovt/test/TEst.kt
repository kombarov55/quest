package com.ovt.test

import io.reactivex.Observable
import java.util.*

fun main(args: Array<String>) {
    val never = Observable.create<Int> { s ->

    }

    val error = Observable.create<Int> { s ->
        s.onError(Exception())
    }

    val empty = Observable.create<Int> { s ->
        s.onComplete()
    }

    fun range(from: Int, n: Int): Observable<Int> = Observable.create { s ->
        for (i in from .. from + n) {
            s.onNext(i)
        }
        s.onComplete()
    }



    val before = Observable.create<Unit> {
        println("start")
        it.onNext(Unit)
        it.onComplete()
    }.flatMap {
        println("flatmap")
        for (i in 0..3000000000) { }
        println("after loop")
        Observable.just(Unit)
    }.subscribe {
        println("finish")
    }






}
