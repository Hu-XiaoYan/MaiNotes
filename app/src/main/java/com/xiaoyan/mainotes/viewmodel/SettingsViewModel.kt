package com.xiaoyan.mainotes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoyan.mainotes.core.GlobalConfig
import com.xiaoyan.mainotes.network.fetchLxnsPersonalChuniData
import com.xiaoyan.mainotes.network.fetchLxnsPersonalMaiData
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private var maiStatus by mutableStateOf(false)
    private var chuStatus by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchLxnsPlayerData(lxnsToken: String) {
        val config = GlobalConfig.getUserConfig().copy()
        //获取配置的拷贝, 防止外部修改
        viewModelScope.launch {
            if (lxnsToken.isBlank()) {
                errorMessage = "TokenEmpty"
            }
            try {
                val maiResult = fetchLxnsPersonalMaiData(lxnsToken)
                if (maiResult.success && maiResult.data != null) {
                    maiStatus = true
                }
                val chuResult = fetchLxnsPersonalChuniData(lxnsToken)
                if (chuResult.success && chuResult.data != null) {
                    chuStatus = true
                }
                //抓取数据
                when {
                    maiStatus && chuStatus -> {
                        config.lxnsPersonalToken = lxnsToken
                        config.lxnsPlayerDataMai = maiResult.data
                        config.lxnsPlayerDataChuni = chuResult.data
                        GlobalConfig.save(config)
                        errorMessage = null
                    }

                    maiStatus && !chuStatus -> {
                        config.lxnsPersonalToken = lxnsToken
                        config.lxnsPlayerDataMai = maiResult.data
                        GlobalConfig.save(config)
                        errorMessage = null
                    }

                    !maiStatus && chuStatus -> {
                        config.lxnsPersonalToken = lxnsToken
                        config.lxnsPlayerDataChuni = chuResult.data
                        GlobalConfig.save(config)
                        errorMessage = null
                    }

                    else -> errorMessage = "fetchError"
                }

            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            }
        }
    }
}