package ru.lct2026.finedu.productcore.data.local

import android.content.Context
import java.io.File

/** `files/datastore/game.json` — там же, где его хранил `Context.dataStoreFile`: прогресс сохраняется. */
fun gameStoragePath(context: Context): GameStoragePath =
    GameStoragePath(File(context.filesDir, "datastore/$GAME_FILE").absolutePath)
