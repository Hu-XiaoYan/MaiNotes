package com.xiaoyan.mainotes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LxnsPersonalApiRespMai(
    val code: Short,
    val success: Boolean,
    val data: LxnsPlayerDataMai? = null,
    val message: String? = null
)

@Serializable
data class LxnsPlayerDataMai(
    val name: String,
    val rating: Int,
    @SerialName("friend_code")
    val friendCode: Long,
    val trophy: Trophy,
    @SerialName("course_rank")
    val courseRank: Short,
    @SerialName("class_rank")
    val classRank: Short,
    val star: Short,
    val icon: PlayerIcon,
    @SerialName("name_plate")
    val namePlate: NamePlate,
    val frame: Frame,
    @SerialName("upload_time")
    val uploadTime: String,
)

@Serializable
data class Trophy(
    val id: Int,
    val name: String,
    val color: String
)

@Serializable
data class PlayerIcon(
    val id: Int,
    val name: String,
    val genre: String
)

@Serializable
data class NamePlate(
    val id: Int,
    val name: String,
    val genre: String
)

@Serializable
data class Frame(
    val id: Int,
    val name: String,
    val genre: String
)
