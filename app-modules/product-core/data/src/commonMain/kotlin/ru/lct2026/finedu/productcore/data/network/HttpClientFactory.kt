package ru.lct2026.finedu.productcore.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Инфраструктура для сети. Сетевых запросов в приложении нет (ограничение ТЗ): клиент создаётся,
 * только если его кто-то запросит из графа. Новый запрос — только после изменения ТЗ.
 */
internal fun createHttpClient(engine: HttpClientEngine, json: Json): HttpClient = HttpClient(engine) {
    install(ContentNegotiation) { json(json) }
    expectSuccess = true
}

/** Движок платформы: OkHttp на Android, Darwin (NSURLSession) на iOS. */
internal expect fun platformHttpEngine(): HttpClientEngine
