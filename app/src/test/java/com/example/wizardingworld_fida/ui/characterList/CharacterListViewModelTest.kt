package com.example.wizardingworld_fida.ui.characterList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.data.repository.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CharacterListViewModelTest{

    private lateinit var viewModel: CharacterListViewModel
    private lateinit var repository: FakeRepository
    val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
        repository = FakeRepository()
        viewModel = CharacterListViewModel(repository)
    }

    @Test
    fun `getFavoritesFromDb with data`(){
        repository.list.add(CharacterDetailModel(id = 1, name = "Test1"))
        repository.list.add(CharacterDetailModel(id = 2, name = "Test2"))
        viewModel.getFavoritesFromDb()
        viewModel.favoriteCharacters.observeForever {
            assertEquals(repository.list,it)
        }
    }

    @Test
    fun `getFavoritesFromDb with empty db`(){
        viewModel.getFavoritesFromDb()
        viewModel.favoriteCharacters.observeForever {
            assertEquals(listOf<CharacterDetailModel>(),it)
        }
    }

    @Test
    fun `deleteFromFavorite`(){
        repository.list.add(CharacterDetailModel(id = 1, name = "Test1"))
        repository.list.add(CharacterDetailModel(id = 2, name = "Test2"))
        viewModel.deleteFromFavorite(CharacterDetailModel(id = 1, name = "Test1"))
        assertEquals(listOf(CharacterDetailModel(id = 2, name = "Test2")),repository.list)
    }

}