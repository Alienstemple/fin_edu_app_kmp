package ru.lct2026.finedu.productcore.data.local

import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

/** Файл профиля в Documents приложения. */
fun gameStoragePath(): GameStoragePath {
    val documents = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true).first()
    return GameStoragePath("$documents/$GAME_FILE")
}
