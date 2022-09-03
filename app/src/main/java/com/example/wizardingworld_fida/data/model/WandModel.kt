package com.example.wizardingworld_fida.data.model


import com.google.gson.annotations.SerializedName

data class WandModel(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? =""
)