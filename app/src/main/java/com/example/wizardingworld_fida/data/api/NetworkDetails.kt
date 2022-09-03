package com.example.wizardingworld_fida.data.api

import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkDetails {


    @GET("api/v1/characters")
    suspend fun getCharacters(@Query("page")page:Int = 1) : ArrayList<CharacterItemModel>

    @GET("api/v1/characters/{id}")
    suspend fun getCharacterDetail(@Path("id")id: Int) : CharacterDetailModel

}