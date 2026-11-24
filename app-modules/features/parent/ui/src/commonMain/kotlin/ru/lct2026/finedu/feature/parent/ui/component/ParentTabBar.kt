package ru.lct2026.finedu.feature.parent.ui.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.lct2026.finedu.feature.parent.ui.ParentTab
import ru.lct2026.finedu.feature.parent.ui.Res
import ru.lct2026.finedu.feature.parent.ui.ic_parent_book
import ru.lct2026.finedu.feature.parent.ui.ic_parent_gear
import ru.lct2026.finedu.feature.parent.ui.ic_parent_shield
import ru.lct2026.finedu.feature.parent.ui.parent_tab_articles
import ru.lct2026.finedu.feature.parent.ui.parent_tab_progress
import ru.lct2026.finedu.feature.parent.ui.parent_tab_scams
import ru.lct2026.finedu.feature.parent.ui.parent_tab_settings
import ru.lct2026.finedu.productcore.ui.Res as CoreRes
import ru.lct2026.finedu.productcore.ui.components.GlassStyle
import ru.lct2026.finedu.productcore.ui.components.glass
import ru.lct2026.finedu.productcore.ui.ic_nav_progress
import ru.lct2026.finedu.productcore.ui.preview.FinEduPreview
import ru.lct2026.finedu.productcore.ui.theme.FinEduTheme

/** Нижняя панель раздела для взрослого: Прогресс / Статьи / Мошенники / Настройки. */
@Composable
internal fun ParentTabBar(selected: ParentTab, onSelect: (ParentTab) -> Unit, modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier.glass(shape = MaterialTheme.shapes.extraLarge, style = GlassStyle.Strong),
        containerColor = Color.Transparent,
        windowInsets = WindowInsets(0)
    ) {
        ParentTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = tab == selected,
                onClick = { onSelect(tab) },
                icon = { Icon(painter = painterResource(tab.iconRes), contentDescription = null) },
                label = { Text(text = stringResource(tab.labelRes), style = MaterialTheme.typography.labelMedium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = FinEduTheme.colors.onGold,
                    indicatorColor = FinEduTheme.colors.gold,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

internal val ParentTab.labelRes: StringResource
    get() = when (this) {
        ParentTab.PROGRESS -> Res.string.parent_tab_progress
        ParentTab.ARTICLES -> Res.string.parent_tab_articles
        ParentTab.SCAMS -> Res.string.parent_tab_scams
        ParentTab.SETTINGS -> Res.string.parent_tab_settings
    }

private val ParentTab.iconRes: DrawableResource
    get() = when (this) {
        ParentTab.PROGRESS -> CoreRes.drawable.ic_nav_progress
        ParentTab.ARTICLES -> Res.drawable.ic_parent_book
        ParentTab.SCAMS -> Res.drawable.ic_parent_shield
        ParentTab.SETTINGS -> Res.drawable.ic_parent_gear
    }

@Preview
@Composable
private fun ParentTabBarPreview() {
    FinEduPreview {
        ParentTabBar(selected = ParentTab.SCAMS, onSelect = {})
    }
}
