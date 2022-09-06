package com.example.wizardingworld_fida.ui.characterList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.data.repository.Repository
import com.example.wizardingworld_fida.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    lateinit var favoriteCharacters: LiveData<List<CharacterDetailModel>>

    fun getFavoritesFromDb(){
        favoriteCharacters = repository.getFavorites().asLiveData()
    }

    fun deleteFromFavorite(characterDetailModel: CharacterDetailModel){
        viewModelScope.launch {
            repository.deleteFavorite(characterDetailModel)
        }
    }


    private val _characters: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val characters: StateFlow<UiState> get() = _characters

    var characterPage = 1
    var characterResponse: ArrayList<CharacterItemModel>? =null

    init {
        getCharactersFromApi()
    }

    fun getCharactersFromApi(){
        viewModelScope.launch {
            _characters.value = UiState.Loading
            val result = repository.getCharacters(characterPage)
            if(result.isEmpty()){
                _characters.value = UiState.Error("empty response")
                Log.d("Characters","$result")
            }
            else{
                characterPage++
                if(characterResponse == null){
                    characterResponse = result
                }
                else{
                    val oldCharacters = characterResponse
                    val newCharacters = result
                    oldCharacters?.addAll(newCharacters)
                    characterResponse = oldCharacters

                }
                _characters.value = UiState.Success(characterResponse?: result)
                //Log.d("Characters","$result")
            }
        }
    }

}