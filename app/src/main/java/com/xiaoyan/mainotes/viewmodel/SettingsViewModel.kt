package com.xiaoyan.mainotes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoyan.mainotes.network.fetchLxnsPersonalData
import com.xiaoyan.mainotes.dataclass.LxnsPlayerDataMai
import kotlinx.coroutines.launch

class SettingsViewModel: ViewModel() {
    var playerData by mutableStateOf<LxnsPlayerDataMai?>(null)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchLxnsPlayerData(lxnsPersonalToken: String) {
        viewModelScope.launch {
            try {
                val result = fetchLxnsPersonalData(lxnsPersonalToken)
                if (result.success && result.data != null){
                    playerData = result.data
                    errorMessage = null
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