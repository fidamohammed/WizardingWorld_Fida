package com.example.wizardingworld_fida.data.repository

import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getCharacters(page: Int) : ArrayList<CharacterItemModel>

    suspend fun getCharacterDetail(id:Int) : CharacterDetailModel

    suspend fun saveFavoriteIntoDb(character:CharacterDetailModel)

    fun getFavorites() : Flow<List<CharacterDetailModel>>

    suspend fun deleteFavorite(characterDetailModel: CharacterDetailModel)
}