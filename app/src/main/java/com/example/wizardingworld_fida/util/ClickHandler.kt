package com.example.wizardingworld_fida.util

import com.example.wizardingworld_fida.data.model.CharacterItemModel

interface ClickHandler {
    fun clickedCharacterItem(character: CharacterItemModel)
}