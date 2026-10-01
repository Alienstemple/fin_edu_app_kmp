package ru.lct2026.finedu.productcore.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

internal actual fun platformHttpEngine(): HttpClientEngine = Darwin.create()
