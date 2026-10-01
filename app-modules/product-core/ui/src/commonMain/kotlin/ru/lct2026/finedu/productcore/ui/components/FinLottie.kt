package ru.lct2026.finedu.productcore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import io.github.alexzhirkevich.compottie.LottieClipSpec
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.dynamic.LottieDynamicProperties
import io.github.alexzhirkevich.compottie.rememberLottieAnimatable
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import ru.lct2026.finedu.productcore.ui.Res
import ru.lct2026.finedu.productcore.ui.theme.FinEduTheme

/**
 * Lottie-анимация из `composeResources/files` модуля `product-core:ui` ([animationPath], например
 * `files/anim_dzyn.json`). Загрузка только из ресурсов приложения: сетевые запросы запрещены ТЗ.
 * [marker] ограничивает проигрывание одним маркером композиции (например, эмоцией Дзыня).
 * В «Спокойном режиме» ([FinEduTheme.reduceMotion]) вместо анимации — статичный кадр: начало [marker]
 * или последний кадр, если маркер не задан.
 * [contentDescription] = `null` — анимация декоративная, TalkBack её пропускает.
 */
@Composable
fun FinLottie(
    animationPath: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    iterations: Int = 1,
    marker: String? = null,
    dynamicProperties: LottieDynamicProperties? = null
) {
    val composition by rememberLottieComposition(animationPath) {
        LottieCompositionSpec.JsonString(Res.readBytes(animationPath).decodeToString())
    }
    val clipSpec = marker?.let { LottieClipSpec.Marker(it) }
    val painter = if (FinEduTheme.reduceMotion) {
        val animatable = rememberLottieAnimatable()
        LaunchedEffect(composition, clipSpec) {
            // Бесконечная скорость не проигрывает клип, а сразу ставит его конечный кадр: при отрицательной —
            // начало маркера, без маркера — последний кадр композиции.
            animatable.animate(
                composition = composition,
                clipSpec = clipSpec,
                speed = if (clipSpec != null) Float.NEGATIVE_INFINITY else Float.POSITIVE_INFINITY
            )
        }
        rememberLottiePainter(
            composition = composition,
            progress = { animatable.progress },
            dynamicProperties = dynamicProperties
        )
    } else {
        rememberLottiePainter(
            composition = composition,
            clipSpec = clipSpec,
            iterations = iterations,
            dynamicProperties = dynamicProperties
        )
    }
    val semanticsModifier = if (contentDescription != null) {
        modifier.semantics { this.contentDescription = contentDescription }
    } else {
        modifier
    }
    Image(painter = painter, contentDescription = null, modifier = semanticsModifier)
}
