package com.xiaoyan.mainotes.local

import android.content.Context
import java.io.File
import com.xiaoyan.mainotes.dataclass.UserConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream

val JSON = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

open class ConfigManager (context: Context) {
    private var configFile: File = File(context.filesDir, "config.json")
    private var config: UserConfig = UserConfig()

    init {
        if (!configFile.exists()) {
            configFile.createNewFile()
            save()
        } else {
            read()
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun read() {
        val configInputStream = configFile.inputStream()
        config = JSON.decodeFromStream(configInputStream)
        configInputStream.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun save() {
        val configOutputStream = configFile.outputStream()
        JSON.encodeToStream(config, configOutputStream)
        configOutputStream.close()
    }
}