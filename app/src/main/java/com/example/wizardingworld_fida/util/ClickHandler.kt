package com.example.wizardingworld_fida.util

import com.example.wizardingworld_fida.data.model.CharacterDetailModel

interface ClickHandler {
    fun <T>clickedCharacterItem(character: T)
}