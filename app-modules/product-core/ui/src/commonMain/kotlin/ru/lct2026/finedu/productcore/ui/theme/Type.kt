package ru.lct2026.finedu.productcore.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import ru.lct2026.finedu.productcore.ui.Res
import ru.lct2026.finedu.productcore.ui.commissioner_bold
import ru.lct2026.finedu.productcore.ui.commissioner_medium
import ru.lct2026.finedu.productcore.ui.commissioner_regular
import ru.lct2026.finedu.productcore.ui.commissioner_semibold

// Commissioner (SIL OFL 1.1), лицензия — docs/licenses/Commissioner-OFL.txt.
// Шрифты из composeResources загружаются в композиции, поэтому семейство и типографика — @Composable.
internal val Commissioner: FontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.commissioner_regular, FontWeight.Normal),
        Font(Res.font.commissioner_medium, FontWeight.Medium),
        Font(Res.font.commissioner_semibold, FontWeight.SemiBold),
        Font(Res.font.commissioner_bold, FontWeight.Bold)
    )

@Composable
private fun style(size: Int, lineHeight: Int, weight: FontWeight) = TextStyle(
    fontFamily = Commissioner,
    fontSize = size.sp,
    lineHeight = lineHeight.sp,
    fontWeight = weight
)

// Размеры — по макету, но основной текст не меньше 16sp (ТЗ 3.6): подписи макета 13–15px подняты до 14–16sp.
internal val FinEduTypography: Typography
    @Composable
    get() = Typography(
        // Крупные суммы: баланс, копилка.
        displayMedium = style(size = 40, lineHeight = 46, weight = FontWeight.Bold),
        displaySmall = style(size = 34, lineHeight = 40, weight = FontWeight.Bold),
        // Заголовки экранов.
        headlineLarge = style(size = 28, lineHeight = 34, weight = FontWeight.Bold),
        headlineMedium = style(size = 24, lineHeight = 30, weight = FontWeight.Bold),
        headlineSmall = style(size = 22, lineHeight = 28, weight = FontWeight.Bold),
        // Заголовки блоков и карточек.
        titleLarge = style(size = 20, lineHeight = 26, weight = FontWeight.Bold),
        titleMedium = style(size = 17, lineHeight = 22, weight = FontWeight.Bold),
        titleSmall = style(size = 16, lineHeight = 22, weight = FontWeight.Bold),
        // Текст.
        bodyLarge = style(size = 17, lineHeight = 26, weight = FontWeight.Normal),
        bodyMedium = style(size = 16, lineHeight = 24, weight = FontWeight.Normal),
        bodySmall = style(size = 14, lineHeight = 20, weight = FontWeight.Normal),
        // Кнопки и бирки.
        labelLarge = style(size = 17, lineHeight = 22, weight = FontWeight.Bold),
        labelMedium = style(size = 15, lineHeight = 20, weight = FontWeight.SemiBold),
        labelSmall = style(size = 13, lineHeight = 18, weight = FontWeight.Bold).copy(letterSpacing = 0.06.em)
    )

/** Во сколько раз «Крупный шрифт» увеличивает текст — поверх системного масштаба шрифта. */
internal const val LARGE_FONT_SCALE = 1.2f

internal fun Typography.scaled(factor: Float): Typography {
    fun TextStyle.scaled() = copy(fontSize = fontSize * factor, lineHeight = lineHeight * factor)
    return copy(
        displayLarge = displayLarge.scaled(),
        displayMedium = displayMedium.scaled(),
        displaySmall = displaySmall.scaled(),
        headlineLarge = headlineLarge.scaled(),
        headlineMedium = headlineMedium.scaled(),
        headlineSmall = headlineSmall.scaled(),
        titleLarge = titleLarge.scaled(),
        titleMedium = titleMedium.scaled(),
        titleSmall = titleSmall.scaled(),
        bodyLarge = bodyLarge.scaled(),
        bodyMedium = bodyMedium.scaled(),
        bodySmall = bodySmall.scaled(),
        labelLarge = labelLarge.scaled(),
        labelMedium = labelMedium.scaled(),
        labelSmall = labelSmall.scaled()
    )
}
