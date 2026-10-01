package ru.lct2026.finedu.productcore.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.pluralStringResource
import ru.lct2026.finedu.productcore.domain.model.Bag
import ru.lct2026.finedu.productcore.domain.model.Dzynki
import ru.lct2026.finedu.productcore.domain.model.PetStat
import ru.lct2026.finedu.productcore.ui.Res
import ru.lct2026.finedu.productcore.ui.bag_needs
import ru.lct2026.finedu.productcore.ui.bag_savings
import ru.lct2026.finedu.productcore.ui.bag_wants
import ru.lct2026.finedu.productcore.ui.dzynki_amount
import ru.lct2026.finedu.productcore.ui.ic_bag_needs
import ru.lct2026.finedu.productcore.ui.ic_bag_savings
import ru.lct2026.finedu.productcore.ui.ic_bag_wants
import ru.lct2026.finedu.productcore.ui.ic_stat_calm
import ru.lct2026.finedu.productcore.ui.ic_stat_charge
import ru.lct2026.finedu.productcore.ui.ic_stat_vibe
import ru.lct2026.finedu.productcore.ui.stat_calm
import ru.lct2026.finedu.productcore.ui.stat_charge
import ru.lct2026.finedu.productcore.ui.stat_vibe
import ru.lct2026.finedu.productcore.ui.theme.FinEduTheme

// Цвет, иконка и подпись мешочков и показателей — всегда вместе: смысл не только в цвете (ТЗ 3.6).

val Bag.color: Color
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        Bag.NEEDS -> FinEduTheme.colors.needs
        Bag.WANTS -> FinEduTheme.colors.wants
        Bag.SAVINGS -> FinEduTheme.colors.savings
    }

val Bag.iconRes: DrawableResource
    get() = when (this) {
        Bag.NEEDS -> Res.drawable.ic_bag_needs
        Bag.WANTS -> Res.drawable.ic_bag_wants
        Bag.SAVINGS -> Res.drawable.ic_bag_savings
    }

val Bag.labelRes: StringResource
    get() = when (this) {
        Bag.NEEDS -> Res.string.bag_needs
        Bag.WANTS -> Res.string.bag_wants
        Bag.SAVINGS -> Res.string.bag_savings
    }

/** Показатель окрашен цветом своего мешочка: Заряд ← Нужное, Вайб ← Хочу, Спокойствие ← Копилка. */
val PetStat.color: Color
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        PetStat.CHARGE -> Bag.NEEDS.color
        PetStat.VIBE -> Bag.WANTS.color
        PetStat.CALM -> Bag.SAVINGS.color
    }

val PetStat.iconRes: DrawableResource
    get() = when (this) {
        PetStat.CHARGE -> Res.drawable.ic_stat_charge
        PetStat.VIBE -> Res.drawable.ic_stat_vibe
        PetStat.CALM -> Res.drawable.ic_stat_calm
    }

val PetStat.labelRes: StringResource
    get() = when (this) {
        PetStat.CHARGE -> Res.string.stat_charge
        PetStat.VIBE -> Res.string.stat_vibe
        PetStat.CALM -> Res.string.stat_calm
    }

/** «30 дзынек», «1 дзынька», «2 дзыньки». */
@Composable
fun dzynkiText(amount: Int): String = pluralStringResource(Res.plurals.dzynki_amount, amount, amount)

@Composable
fun dzynkiText(amount: Dzynki): String = dzynkiText(amount.amount)
