package ru.lct2026.finedu.feature.period.ui.component

import org.jetbrains.compose.resources.StringResource
import ru.lct2026.finedu.feature.period.ui.Res
import ru.lct2026.finedu.feature.period.ui.period_stage_baby
import ru.lct2026.finedu.feature.period.ui.period_stage_master
import ru.lct2026.finedu.feature.period.ui.period_stage_spry
import ru.lct2026.finedu.productcore.domain.model.PetStage

internal val PetStage.labelRes: StringResource
    get() = when (this) {
        PetStage.BABY -> Res.string.period_stage_baby
        PetStage.SPRY -> Res.string.period_stage_spry
        PetStage.MASTER -> Res.string.period_stage_master
    }
