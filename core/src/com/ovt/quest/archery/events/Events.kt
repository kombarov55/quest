package com.ovt.quest.archery.events

import com.ovt.quest.archery.events.dto.BowFiredDto
import com.ovt.quest.archery.events.dto.GroundHitDto
import com.ovt.quest.archery.events.dto.TargetHitDto
import com.ovt.quest.archery.events.dto.TouchDownDto
import io.reactivex.subjects.PublishSubject

object Events {

    val touchDown: PublishSubject<TouchDownDto> = PublishSubject.create()
    val bowFired: PublishSubject<BowFiredDto> = PublishSubject.create()
    val targetHit: PublishSubject<TargetHitDto> = PublishSubject.create()
    val groundHit: PublishSubject<GroundHitDto> = PublishSubject.create()

}