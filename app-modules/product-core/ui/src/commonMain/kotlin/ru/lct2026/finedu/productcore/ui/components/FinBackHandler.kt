package ru.lct2026.finedu.productcore.ui.components

import androidx.compose.runtime.Composable
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState

/**
 * Системное «Назад» (жест, кнопка) внутри экрана: закрыть шторку, статью, ритуал.
 * Вызывать безусловно, управлять — через [enabled]: при выключенном срабатывает навигация по умолчанию.
 */
@Composable
fun FinBackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    NavigationBackHandler(
        state = rememberNavigationEventState(NavigationEventInfo.None),
        isBackEnabled = enabled,
        onBackCompleted = onBack
    )
}
