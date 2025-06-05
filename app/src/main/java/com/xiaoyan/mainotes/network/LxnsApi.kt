package com.xiaoyan.mainotes.network

import com.xiaoyan.mainotes.dataclass.LxnsPersonalApiRespMai
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}

suspend fun fetchLxnsPersonalData(lxnsPersonalToken: String): LxnsPersonalApiRespMai {
    val resp: LxnsPersonalApiRespMai =
        client.get("https://maimai.lxns.net/api/v0/user/maimai/player") {
            header("X-User-Token", lxnsPersonalToken)
        }.body()
    return resp
}