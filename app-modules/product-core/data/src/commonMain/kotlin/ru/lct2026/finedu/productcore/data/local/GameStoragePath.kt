package ru.lct2026.finedu.productcore.data.local

/** Имя файла профиля. Каталог задаёт платформа: на Android — тот же файл, что и до перехода на KMP. */
internal const val GAME_FILE = "game.json"

/** Абсолютный путь к файлу профиля. Платформа создаёт его (`gameStoragePath`) и отдаёт корневому компоненту. */
class GameStoragePath(val path: String)
