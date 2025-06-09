package com.xiaoyan.mainotes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoyan.mainotes.model.LxnsPlayerDataMai
import com.xiaoyan.mainotes.network.fetchLxnsPersonalMaiData
import kotlinx.coroutines.launch

class SettingsViewModel: ViewModel() {
    private var playerData by mutableStateOf<LxnsPlayerDataMai?>(null)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchLxnsPlayerData(lxnsPersonalToken: String) {
        if (lxnsPersonalToken.isBlank()) {
            errorMessage = "TokenEmpty"
            return
        }
        //Token为空拒绝抓取
        viewModelScope.launch {
            try {
                val result = fetchLxnsPersonalMaiData(lxnsPersonalToken)
                if (result.success && result.data != null){
                    playerData = result.data
                    errorMessage = null
                    //抓取成功
                    val config = GlobalConfig.getUserConfig().copy()
                    config.lxnsPersonalToken = lxnsPersonalToken
                    GlobalConfig.save(config)
                } else {
                    playerData = null
                    errorMessage = result.message
                    //抓取失败
                }
            } catch (e: Exception){
                playerData = null
                errorMessage = e.localizedMessage
                //网络错误等
            }
        }
    }
}