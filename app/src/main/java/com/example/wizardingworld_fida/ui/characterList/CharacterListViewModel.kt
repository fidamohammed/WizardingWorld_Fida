package com.example.wizardingworld_fida.ui.characterList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.data.repository.Repository
import com.example.wizardingworld_fida.data.repository.RepositoryImpl
import com.example.wizardingworld_fida.paging.CharacterPagingSource
import com.example.wizardingworld_fida.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.CharacterData
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _characters: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val characters: StateFlow<UiState> get() = _characters

    var characterPage = 1
    var characterResponse: ArrayList<CharacterItemModel>? =null

    lateinit var favoriteCharacters: LiveData<List<CharacterDetailModel>>

    fun getFavoritesFromDb(){
        favoriteCharacters = repository.getFavorites().asLiveData()
    }

    fun deleteFromFavorite(characterDetailModel: CharacterDetailModel){
        viewModelScope.launch {
            repository.deleteFavorite(characterDetailModel)
        }

    }

//    init {
//        getCharactersFromApi()
//    }

    fun getListData(): Flow<PagingData<CharacterItemModel>> {
        return Pager (config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = { CharacterPagingSource(repository) }).flow.cachedIn(viewModelScope)
    }

    fun getCharactersFromApi(){
        viewModelScope.launch {
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

                }
                _characters.value = UiState.Success(characterResponse?: result)
                Log.d("Characters","$result")
            }
        }
    }

}