package com.ovt.quest.quest.commons

import io.reactivex.subjects.PublishSubject


object UIEvents {

    val toggleSettings = PublishSubject.create<Boolean>()
    val toggleDiary = PublishSubject.create<Boolean>()
    val homePressed = PublishSubject.create<Unit>()

}