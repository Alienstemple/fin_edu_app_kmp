package ru.lct2026.finedu.productcore.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import ru.lct2026.finedu.productcore.data.Res
import ru.lct2026.finedu.productcore.data.content.ContentParser
import ru.lct2026.finedu.productcore.domain.model.GameContent
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository

/** Контент читается из `composeResources/files/content` один раз за запуск. */
internal class ContentRepositoryImpl(private val parser: ContentParser) : ContentRepository {

    private val mutex = Mutex()
    private var content: GameContent? = null

    override suspend fun content(): GameContent = mutex.withLock {
        content ?: load().also { content = it }
    }

    private suspend fun load(): GameContent {
        val files = ContentParser.FILES.associateWith { fileName ->
            Res.readBytes("files/${ContentParser.DIRECTORY}/$fileName").decodeToString()
        }
        return withContext(Dispatchers.Default) { parser.parse(files::getValue) }
    }
}
