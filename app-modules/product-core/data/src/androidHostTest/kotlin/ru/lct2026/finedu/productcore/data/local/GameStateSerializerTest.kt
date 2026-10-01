package ru.lct2026.finedu.productcore.data.local

import androidx.datastore.core.CorruptionException
import kotlinx.coroutines.test.runTest
import okio.Buffer
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.lct2026.finedu.productcore.data.TestGame

class GameStateSerializerTest {

    private val serializer = GameStateSerializer(TestGame.json)

    @Test(expected = CorruptionException::class)
    fun `повреждённый файл — ошибка повреждения, а не падение`() = runTest {
        serializer.readFrom(Buffer().writeUtf8("{не json"))
    }

    @Test(expected = CorruptionException::class)
    fun `неизвестное значение enum — тоже повреждение`() = runTest {
        serializer.readFrom(
            Buffer().writeUtf8("""{"state":{"profile":{"playerName":"","petName":"","fur":"GOLD","hat":"NONE",""")
        )
    }

    @Test
    fun `по умолчанию профиля нет`() {
        assertEquals(StoredGameDto(), serializer.defaultValue)
    }
}
