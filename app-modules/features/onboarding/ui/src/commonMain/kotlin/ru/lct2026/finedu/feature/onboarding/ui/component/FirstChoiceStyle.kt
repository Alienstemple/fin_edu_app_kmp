package ru.lct2026.finedu.feature.onboarding.ui.component

import org.jetbrains.compose.resources.StringResource
import ru.lct2026.finedu.feature.onboarding.ui.FirstChoice
import ru.lct2026.finedu.feature.onboarding.ui.Res
import ru.lct2026.finedu.feature.onboarding.ui.onboarding_choice_cookie
import ru.lct2026.finedu.feature.onboarding.ui.onboarding_choice_porridge
import ru.lct2026.finedu.feature.onboarding.ui.onboarding_result_cookie_line
import ru.lct2026.finedu.feature.onboarding.ui.onboarding_result_cookie_tag
import ru.lct2026.finedu.feature.onboarding.ui.onboarding_result_cookie_title
import ru.lct2026.finedu.feature.onboarding.ui.onboarding_result_cookie_why
import ru.lct2026.finedu.feature.onboarding.ui.onboarding_result_porridge_line
import ru.lct2026.finedu.feature.onboarding.ui.onboarding_result_porridge_tag
import ru.lct2026.finedu.feature.onboarding.ui.onboarding_result_porridge_title
import ru.lct2026.finedu.feature.onboarding.ui.onboarding_result_porridge_why
import ru.lct2026.finedu.productcore.domain.model.Bag
import ru.lct2026.finedu.productcore.domain.model.PetStat
import ru.lct2026.finedu.productcore.ui.illustration.PetMood

// Мешочек, показатель, реакция и тексты учебного выбора — вместе, чтобы варианты не разъехались.

internal val FirstChoice.bag: Bag
    get() = when (this) {
        FirstChoice.PORRIDGE -> Bag.NEEDS
        FirstChoice.COOKIE -> Bag.WANTS
    }

internal val FirstChoice.stat: PetStat
    get() = when (this) {
        FirstChoice.PORRIDGE -> PetStat.CHARGE
        FirstChoice.COOKIE -> PetStat.VIBE
    }

internal val FirstChoice.mood: PetMood
    get() = when (this) {
        FirstChoice.PORRIDGE -> PetMood.HAPPY
        FirstChoice.COOKIE -> PetMood.JOY
    }

internal val FirstChoice.titleRes: StringResource
    get() = when (this) {
        FirstChoice.PORRIDGE -> Res.string.onboarding_choice_porridge
        FirstChoice.COOKIE -> Res.string.onboarding_choice_cookie
    }

internal val FirstChoice.lineRes: StringResource
    get() = when (this) {
        FirstChoice.PORRIDGE -> Res.string.onboarding_result_porridge_line
        FirstChoice.COOKIE -> Res.string.onboarding_result_cookie_line
    }

internal val FirstChoice.tagRes: StringResource
    get() = when (this) {
        FirstChoice.PORRIDGE -> Res.string.onboarding_result_porridge_tag
        FirstChoice.COOKIE -> Res.string.onboarding_result_cookie_tag
    }

internal val FirstChoice.resultTitleRes: StringResource
    get() = when (this) {
        FirstChoice.PORRIDGE -> Res.string.onboarding_result_porridge_title
        FirstChoice.COOKIE -> Res.string.onboarding_result_cookie_title
    }

internal val FirstChoice.whyRes: StringResource
    get() = when (this) {
        FirstChoice.PORRIDGE -> Res.string.onboarding_result_porridge_why
        FirstChoice.COOKIE -> Res.string.onboarding_result_cookie_why
    }
