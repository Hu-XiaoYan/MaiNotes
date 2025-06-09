package com.xiaoyan.mainotes

import android.app.Application
import com.xiaoyan.mainotes.viewmodel.GlobalConfig


class MaiNotes: Application(){
    override fun onCreate() {
        super.onCreate()
        application = this
        GlobalConfig.init(this)
    }
    companion object {
        lateinit var application: MaiNotes
    }
}