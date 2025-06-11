package com.xiaoyan.mainotes.model

import kotlinx.serialization.Serializable

@Serializable
data class UserConfig(
    var lxnsPersonalToken: String? = null,
    var lxnsPlayerDataMai: LxnsPlayerDataMai? = null,
    var lxnsPlayerDataChuni: LxnsPlayerDataChuni? = null
)