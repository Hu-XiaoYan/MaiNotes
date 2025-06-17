package com.xiaoyan.mainotes.network

import com.xiaoyan.mainotes.model.LxnsPersonalApiRespChuni
import com.xiaoyan.mainotes.model.LxnsPersonalApiRespMai
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            explicitNulls = false
            isLenient = true
        })
    }
}

suspend fun fetchLxnsPersonalMaiData(lxnsPersonalToken: String): LxnsPersonalApiRespMai {
    val resp: LxnsPersonalApiRespMai =
        client.get("https://maimai.lxns.net/api/v0/user/maimai/player") {
            header("X-User-Token", lxnsPersonalToken)
        }.body()
    val parsedTime = Instant.parse(resp.data?.uploadTime)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    val newData = resp.data?.copy(uploadTime = parsedTime)
    return resp.copy(data = newData)
}


suspend fun fetchLxnsPersonalChuniData(lxnsPersonalToken: String): LxnsPersonalApiRespChuni {
    val resp: LxnsPersonalApiRespChuni =
        client.get("https://maimai.lxns.net/api/v0/user/chunithm/player") {
            header("X-User-Token", lxnsPersonalToken)
        }.body()
    val parsedTime = Instant.parse(resp.data?.uploadTime)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    val newData = resp.data?.copy(uploadTime = parsedTime)
    return resp.copy(data = newData)
}