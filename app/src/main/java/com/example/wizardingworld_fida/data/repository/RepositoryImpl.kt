package com.example.wizardingworld_fida.data.repository

import com.example.wizardingworld_fida.data.api.NetworkDetails
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.data.room.CharacterDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val networkDetails: NetworkDetails, val characterDao: CharacterDao) : Repository {
    override suspend fun getCharacters(page: Int): ArrayList<CharacterItemModel> =
        networkDetails.getCharacters(page)

    override suspend fun getCharacterDetail(id: Int): CharacterDetailModel =
        networkDetails.getCharacterDetail(id)

    override suspend fun saveFavoriteIntoDb(character: CharacterDetailModel) {
        characterDao.saveFavoriteInDb(character)
    }

    override fun getFavorites(): Flow<List<CharacterDetailModel>> = characterDao.getFavoritesFromDb()

    override suspend fun deleteFavorite(characterDetailModel: CharacterDetailModel) {
        characterDao.deleteFavorite(characterDetailModel)
    }

    override fun checkIfFavorite(id: Int): Flow<CharacterDetailModel> =
        characterDao.checkFavoriteExist(id)



}