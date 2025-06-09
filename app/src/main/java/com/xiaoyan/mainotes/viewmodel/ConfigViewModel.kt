package com.xiaoyan.mainotes.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.xiaoyan.mainotes.local.ConfigManager
import com.xiaoyan.mainotes.model.UserConfig

object GlobalConfig : ViewModel() {
    private lateinit var configManager: ConfigManager

    fun init(applicationContext: Context) {
        val context = applicationContext.applicationContext
        configManager = ConfigManager(context)
    }

    fun getUserConfig() = configManager.config

    fun save(config: UserConfig) {
        configManager.config = config
        configManager.save()
    }

    fun read(): UserConfig {
        configManager.read()
        return configManager.config
    }
}