package com.xiaoyan.mainotes.core

import android.content.Context
import androidx.lifecycle.ViewModel
import java.io.File
import com.xiaoyan.mainotes.model.UserConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream

val JSON = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    explicitNulls = false
    isLenient = true
}

object GlobalConfig: ViewModel() {
    private class ConfigManager (context: Context) {
        private val configFile: File = File(context.filesDir, "config.json")
        var config: UserConfig = UserConfig()

        init {
            if (!configFile.exists()) {
                configFile.createNewFile()
                save()
            } else {
                read()
            }
        }

        @OptIn(ExperimentalSerializationApi::class)
        fun read() {
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

    private lateinit var configManager: ConfigManager
    fun getUserConfig(): UserConfig = configManager.config

    fun init(applicationContext: Context) {
        configManager = ConfigManager(applicationContext)
    }

    fun save(config: UserConfig) {
        configManager.config = config
        configManager.save()
    }

    fun read(): UserConfig {
        configManager.read()
        return configManager.config
    }

}