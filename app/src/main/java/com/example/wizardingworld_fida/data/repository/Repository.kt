package com.example.wizardingworld_fida.data.repository

import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel

interface Repository {
    suspend fun getCharacters(page: Int) : ArrayList<CharacterItemModel>

    suspend fun getCharacterDetail(id:Int) : CharacterDetailModel
}