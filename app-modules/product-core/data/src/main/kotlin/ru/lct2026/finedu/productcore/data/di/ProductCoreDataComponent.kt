package ru.lct2026.finedu.productcore.data.di

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import ru.lct2026.finedu.productcore.data.content.ContentParser
import ru.lct2026.finedu.productcore.data.local.GameStateSerializer
import ru.lct2026.finedu.productcore.data.local.StoredGameDto
import ru.lct2026.finedu.productcore.data.repository.ContentRepositoryImpl
import ru.lct2026.finedu.productcore.data.repository.GameRepositoryImpl
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository

private const val GAME_FILE = "game.json"

/** Конфиг JSON без состояния — общий для профиля и контента, наружу из `data` не выходит. */
private val json = Json { ignoreUnknownKeys = true }

/**
 * Привязки `product-core:data` для корневого kotlin-inject компонента.
 * Реализации `internal`, поэтому создаются здесь, а наружу видны только интерфейсы `domain`.
 */
interface ProductCoreDataComponent {

    /** Повреждённый файл профиля заменяется пустым: приложение не падает, игра начнётся заново. */
    @AppScope
    @Provides
    fun provideGameRepository(context: Context): GameRepository = GameRepositoryImpl(
        DataStoreFactory.create(
            serializer = GameStateSerializer(json),
            corruptionHandler = ReplaceFileCorruptionHandler { StoredGameDto() },
            produceFile = { context.dataStoreFile(GAME_FILE) }
        )
    )

    @AppScope
    @Provides
    fun provideContentRepository(context: Context): ContentRepository =
        ContentRepositoryImpl(context, ContentParser(json))
}
