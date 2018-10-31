package com.ovt.quest.archery.events

import com.ovt.quest.archery.events.dto.ArrowHitDto
import com.ovt.quest.archery.events.dto.BowFiredDto
import io.reactivex.subjects.PublishSubject

object Events {

    val bowFired: PublishSubject<BowFiredDto> = PublishSubject.create()
    val arrowHit: PublishSubject<ArrowHitDto> = PublishSubject.create()

}