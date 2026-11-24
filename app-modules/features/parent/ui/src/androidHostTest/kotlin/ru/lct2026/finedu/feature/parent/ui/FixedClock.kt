package ru.lct2026.finedu.feature.parent.ui

import kotlin.time.Clock
import kotlin.time.Instant

/** Часы с фиксированным временем: тесты не зависят от текущей даты. */
class FixedClock(private val millis: Long) : Clock {
    override fun now(): Instant = Instant.fromEpochMilliseconds(millis)
}
