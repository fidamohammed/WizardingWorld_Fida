package com.example.wizardingworld_fida.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CharacterItemModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String? = "",
    @SerializedName("name")
    val name: String,
    @SerializedName("species")
    val species: String? = ""
) : Serializable