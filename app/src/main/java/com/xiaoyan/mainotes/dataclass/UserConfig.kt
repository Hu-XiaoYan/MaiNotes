package com.xiaoyan.mainotes.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class UserConfig(
    var lxnsPersonalToken: String = ""
)