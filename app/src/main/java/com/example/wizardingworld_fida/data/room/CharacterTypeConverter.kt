package com.example.wizardingworld_fida.data.room

import androidx.room.TypeConverter
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.HouseModel
import com.example.wizardingworld_fida.data.model.WandModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CharacterTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun characterDetailToString(characterDetailModel: CharacterDetailModel): String = gson.toJson(characterDetailModel)

    @TypeConverter
    fun stringToCharacterDetail(data: String): CharacterDetailModel {
        val listType = object : TypeToken<CharacterDetailModel>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun HouseModelToString(houseModel: HouseModel): String = gson.toJson(houseModel)

    @TypeConverter
    fun stringToHouseModel(data: String): HouseModel {
        val listType = object : TypeToken<HouseModel>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun WandModelToString(wandModel: List<WandModel>): String = gson.toJson(wandModel)

    @TypeConverter
    fun stringToWandModel(data: String): List<WandModel> {
        val listType = object : TypeToken<List<WandModel>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun SpeciesToString(species: List<String>): String = gson.toJson(species)

    @TypeConverter
    fun stringToSpecies(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }


}