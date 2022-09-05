package com.example.wizardingworld_fida.data.repository

import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRepository: Repository {

    val list = mutableListOf<CharacterDetailModel>()

    override suspend fun getCharacters(page: Int): ArrayList<CharacterItemModel> {
        return arrayListOf(CharacterItemModel(id = 1, name = "test"))
    }

    override suspend fun getCharacterDetail(id: Int): CharacterDetailModel {
        return CharacterDetailModel(id = 1, name = "test")
    }

    override suspend fun saveFavoriteIntoDb(character: CharacterDetailModel) {
        list.add(character)
    }

    override fun getFavorites(): Flow<List<CharacterDetailModel>> {
        return flowOf(list)
    }

    override suspend fun deleteFavorite(characterDetailModel: CharacterDetailModel) {
        list.remove(characterDetailModel)
    }
}