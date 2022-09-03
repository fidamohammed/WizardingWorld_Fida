package com.example.wizardingworld_fida.data.repository

import com.example.wizardingworld_fida.data.api.NetworkDetails
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val networkDetails: NetworkDetails) : Repository {
    override suspend fun getCharacters(page: Int): ArrayList<CharacterItemModel> =
        networkDetails.getCharacters(page)

    override suspend fun getCharacterDetail(id: Int): CharacterDetailModel =
        networkDetails.getCharacterDetail(id)


}