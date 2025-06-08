package com.xiaoyan.mainotes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoyan.mainotes.model.LxnsPlayerDataMai
import com.xiaoyan.mainotes.network.fetchLxnsPersonalData
import kotlinx.coroutines.launch
import com.xiaoyan.mainotes.MaiNotes.Companion.application

class SettingsViewModel: ViewModel() {
    var playerData by mutableStateOf<LxnsPlayerDataMai?>(null)
    var errorMessage by mutableStateOf<String?>(null)
    private val config = application.configManager.config

    fun fetchLxnsPlayerData(lxnsPersonalToken: String) {
        if (lxnsPersonalToken.isBlank()) {
            errorMessage = "TokenEmpty"
            return
        }
        viewModelScope.launch {
            try {
                val result = fetchLxnsPersonalData(lxnsPersonalToken)
                if (result.success && result.data != null){
                    playerData = result.data
                    errorMessage = null
                    config.lxnsPersonalToken = lxnsPersonalToken
                    application.configManager.save()
                } else {
                    playerData = null
                    errorMessage = result.message
                }
            } catch (e: Exception){
                playerData = null
                errorMessage = e.localizedMessage
            }
        }
    }
}