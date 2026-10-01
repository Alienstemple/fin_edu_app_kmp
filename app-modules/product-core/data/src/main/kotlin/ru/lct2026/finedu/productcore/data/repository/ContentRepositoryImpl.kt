package ru.lct2026.finedu.productcore.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.lct2026.finedu.productcore.data.content.ContentParser
import ru.lct2026.finedu.productcore.domain.model.GameContent
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository

/** Контент читается из assets один раз за запуск. */
internal class ContentRepositoryImpl(private val context: Context, private val parser: ContentParser) :
    ContentRepository {

    private val content: GameContent by lazy {
        parser.parse { fileName ->
            context.assets.open("${ContentParser.DIRECTORY}/$fileName").bufferedReader().use { it.readText() }
        }
    }

    override suspend fun content(): GameContent = withContext(Dispatchers.IO) { content }
}
