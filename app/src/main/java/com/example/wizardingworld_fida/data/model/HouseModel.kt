package com.example.wizardingworld_fida.data.model


import com.google.gson.annotations.SerializedName

data class HouseModel(
    @SerializedName("animal")
    val animal: String? = "",
    @SerializedName("animal_url")
    val animalUrl: String? = "",
    @SerializedName("colors")
    val colors: String? = "",
    @SerializedName("common_room")
    val commonRoom: String? = "",
    @SerializedName("common_room_url")
    val commonRoomUrl: String? = "",
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("element")
    val element: String? = "",
    @SerializedName("element_url")
    val elementUrl: String? = "",
    @SerializedName("founder")
    val founder: String? = "",
    @SerializedName("founder_url")
    val founderUrl: String? = "",
    @SerializedName("ghost")
    val ghost: String? = "",
    @SerializedName("ghost_url")
    val ghostUrl: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: String?= "",
    @SerializedName("image_url")
    val imageUrl: String?= "",
    @SerializedName("members_count")
    val membersCount: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("path")
    val path: String? = "",
    @SerializedName("updated_at")
    val updatedAt: String? = ""
)