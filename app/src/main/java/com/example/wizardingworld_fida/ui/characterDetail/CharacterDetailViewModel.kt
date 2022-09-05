package com.example.wizardingworld_fida.ui.characterDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.repository.Repository
import com.example.wizardingworld_fida.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(val repository: Repository): ViewModel() {

    private val _characterDetail : MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val characterDetail: StateFlow<UiState> get() = _characterDetail

   // lateinit var favoriteCharacters: LiveData<List<CharacterDetailModel>>

    fun getCharacterDetail(id: Int){
        viewModelScope.launch {
            try{
                val result = repository.getCharacterDetail(id)
                _characterDetail.value = UiState.Success(result)
            }
            catch (e: Exception){
                _characterDetail.value = UiState.Error("Error -> ${e.message}")
            }
        }
    }

    fun saveFavoriteToDb(character:CharacterDetailModel){
        viewModelScope.launch {
            repository.saveFavoriteIntoDb(character)
        }
    }

//    fun getFavoritesFromDb(){
//        favoriteCharacters = repository.getFavorites().asLiveData()
//    }

}