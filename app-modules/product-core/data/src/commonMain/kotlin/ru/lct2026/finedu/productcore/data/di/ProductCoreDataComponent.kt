package ru.lct2026.finedu.productcore.data.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.core.okio.OkioStorage
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import okio.FileSystem
import okio.Path.Companion.toPath
import ru.lct2026.finedu.productcore.data.content.ContentParser
import ru.lct2026.finedu.productcore.data.local.GameStateSerializer
import ru.lct2026.finedu.productcore.data.local.GameStoragePath
import ru.lct2026.finedu.productcore.data.local.StoredGameDto
import ru.lct2026.finedu.productcore.data.network.createHttpClient
import ru.lct2026.finedu.productcore.data.network.platformHttpEngine
import ru.lct2026.finedu.productcore.data.repository.ContentRepositoryImpl
import ru.lct2026.finedu.productcore.data.repository.GameRepositoryImpl
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.domain.repository.GameRepository

/** Конфиг JSON без состояния — общий для профиля и контента, наружу из `data` не выходит. */
private val json = Json { ignoreUnknownKeys = true }

/**
 * Привязки `product-core:data` для корневого kotlin-inject компонента.
 * Реализации `internal`, поэтому создаются здесь, а наружу видны только интерфейсы `domain`.
 * [GameStoragePath] даёт платформа (`gameStoragePath`).
 */
interface ProductCoreDataComponent {

    /** Повреждённый файл профиля заменяется пустым: приложение не падает, игра начнётся заново. */
    @AppScope
    @Provides
    fun provideGameRepository(storagePath: GameStoragePath): GameRepository = GameRepositoryImpl(
        DataStoreFactory.create(
            storage = OkioStorage(FileSystem.SYSTEM, GameStateSerializer(json)) { storagePath.path.toPath() },
            corruptionHandler = ReplaceFileCorruptionHandler { StoredGameDto() }
        )
    )

    @AppScope
    @Provides
    fun provideContentRepository(): ContentRepository = ContentRepositoryImpl(ContentParser(json))

    /** Создаётся лениво при первом запросе. Сейчас его никто не запрашивает: сети в приложении нет (ТЗ). */
    @AppScope
    @Provides
    fun provideHttpClient(): HttpClient = createHttpClient(platformHttpEngine(), json)
}
