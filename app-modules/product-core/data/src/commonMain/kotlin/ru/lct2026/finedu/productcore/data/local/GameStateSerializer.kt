package ru.lct2026.finedu.productcore.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

/** Профиль в DataStore — один JSON-документ (UTF-8). */
internal class GameStateSerializer(private val json: Json) : OkioSerializer<StoredGameDto> {

    override val defaultValue: StoredGameDto = StoredGameDto()

    override suspend fun readFrom(source: BufferedSource): StoredGameDto = try {
        json.decodeFromString(StoredGameDto.serializer(), source.readUtf8())
    } catch (e: SerializationException) {
        throw CorruptionException("Не удалось прочитать профиль", e)
    } catch (e: IllegalArgumentException) {
        throw CorruptionException("Не удалось прочитать профиль", e)
    }

    override suspend fun writeTo(t: StoredGameDto, sink: BufferedSink) {
        sink.writeUtf8(json.encodeToString(StoredGameDto.serializer(), t))
    }
}
