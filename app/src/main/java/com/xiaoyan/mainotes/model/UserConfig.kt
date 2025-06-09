package com.xiaoyan.mainotes.model

import kotlinx.serialization.Serializable

@Serializable
data class UserConfig(
    var lxnsPersonalToken: String = "",
    var playerData: PlayerData? = null
)

@Serializable
data class PlayerData(
    var lxnsPlayerDataMai: LxnsPlayerDataMai? = null,
    var lxnsPlayerDataChuni: LxnsPlayerDataChuni? = null
)