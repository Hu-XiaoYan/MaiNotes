package com.xiaoyan.mainotes

import android.app.Application
import com.xiaoyan.mainotes.local.ConfigManager


class MaiNotes: Application(){
    lateinit var configManager: ConfigManager
    override fun onCreate() {
        super.onCreate()
        application = this
        configManager = ConfigManager(this)
    }
    companion object {
        lateinit var application: MaiNotes
    }
}