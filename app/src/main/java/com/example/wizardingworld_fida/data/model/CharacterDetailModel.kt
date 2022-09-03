package com.example.wizardingworld_fida.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Character")
data class CharacterDetailModel(
    @SerializedName("animagus")
    val animagus: String? = "",
    @SerializedName("blood")
    val blood: String? = "",
    @SerializedName("boggart")
    val boggart: String? = "",
    @SerializedName("born")
    val born: String? = "",
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("died")
    val died: String? = "",
    @SerializedName("eyes")
    val eyes: String? = "",
    @SerializedName("family")
    val family: String? = "",
    @SerializedName("feathers")
    val feathers: String? = "",
    @SerializedName("gender")
    val gender: String? = "",
    @SerializedName("hair")
    val hair: String? = "",
    @SerializedName("height")
    val height: String? = "",
    @SerializedName("house")
    val house: HouseModel? = HouseModel(),
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String?="" ,
    @SerializedName("job")
    val job: String? = "",
    @SerializedName("loyalty")
    val loyalty: String? = "",
    @SerializedName("marital")
    val marital: String? = "",
    @SerializedName("name")
    val name: String ,
    @SerializedName("nationality")
    val nationality: String? = "",
    @SerializedName("nickname")
    val nickname: String?= "",
    @SerializedName("patronus")
    val patronus: String? = "",
    @SerializedName("romances")
    val romances: String? = "",
    @SerializedName("signature")
    val signature: String? = "",
    @SerializedName("skin")
    val skin: String? = "",
    @SerializedName("species")
    val species: List<String>,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("wand")
    val wand: List<WandModel?>? = listOf(),
    @SerializedName("weight")
    val weight: String? = ""
) : Serializable