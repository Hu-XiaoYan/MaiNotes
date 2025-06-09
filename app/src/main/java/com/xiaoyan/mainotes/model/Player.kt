package com.xiaoyan.mainotes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LxnsPersonalApiRespChuni(
    val code: Short,
    val success: Boolean,
    val data: LxnsPlayerDataChuni? = null,
    val message: String? = null
)

@Serializable
data class LxnsPlayerDataChuni(
    val name: String,
    val level: String,
    val rating: Float,
    @SerialName("rating_possession")
    val ratingPossession: String,
    @SerialName("friend_code")
    val friendCode: Long,
    @SerialName("class_emblem")
    val classEmblem: ClassEmblem,
    @SerialName("reborn_count")
    val rebornCount: Short,
    @SerialName("over_power_progress")
    val overPowerProgress: Float,
    val currency: Int,
    @SerialName("total_currency")
    val totalCurrency: Int,
    @SerialName("total_play_count")
    val totalPlayCount: Int,
    val trophy: TrophyChuni,
    val character: Character?,
    @SerialName("name_plate")
    val namePlate: NamePlateChuni?,
    @SerialName("map_icon")
    val mapIcon: MapIcon?,
    @SerialName("upload_time")
    val uploadTime: String
)

@Serializable
data class ClassEmblem(
    val base: Short = 0,
    val medal: Short = 0
)

@Serializable
data class TrophyChuni(
    val id: Int,
    val name: String,
    val color: String?,
    val level: Int?
)

@Serializable
data class NamePlateChuni(
    val id: Int,
    val name: String,
    val color: String?,
    val level: Int?
)

@Serializable
data class MapIcon(
    val id: Int,
    val name: String,
    val color: String?,
    val level: Int?
)

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val color: String?,
    val level: Int?
)

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
    val trophy: TrophyMai,
    @SerialName("course_rank")
    val courseRank: Short,
    @SerialName("class_rank")
    val classRank: Short,
    val star: Short,
    val icon: PlayerIcon?,
    @SerialName("name_plate")
    val namePlate: NamePlate?,
    val frame: Frame?,
    @SerialName("upload_time")
    val uploadTime: String
)

@Serializable
data class TrophyMai(
    val id: Int,
    val name: String,
    val color: String?
)

@Serializable
data class PlayerIcon(
    val id: Int,
    val name: String,
    val genre: String?
)

@Serializable
data class NamePlate(
    val id: Int,
    val name: String,
    val genre: String?
)

@Serializable
data class Frame(
    val id: Int,
    val name: String,
    val genre: String?
)
