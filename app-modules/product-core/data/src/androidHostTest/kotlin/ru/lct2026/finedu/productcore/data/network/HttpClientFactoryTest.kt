package ru.lct2026.finedu.productcore.data.network

import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

/** Только MockEngine: реальной сети в приложении и тестах нет (ТЗ). */
class HttpClientFactoryTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `JSON-ответ разбирается в модель, лишние поля игнорируются`() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"value":7,"extra":true}""",
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val probe: Probe = createHttpClient(engine, json).get("https://example.test/probe").body()

        assertEquals(Probe(value = 7), probe)
    }

    @Test(expected = ClientRequestException::class)
    fun `ошибка 4xx — исключение, а не пустой ответ`() = runTest {
        val engine = MockEngine { respondError(HttpStatusCode.NotFound) }

        createHttpClient(engine, json).get("https://example.test/missing")
    }

    @Serializable
    private data class Probe(val value: Int)
}
