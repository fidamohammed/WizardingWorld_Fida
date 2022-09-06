package com.example.wizardingworld_fida.ui.characterDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.repository.RepositoryImpl
import com.example.wizardingworld_fida.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.whenever
import java.io.IOException
import java.net.UnknownHostException

class CharacterDetailViewModelTest{

    val dispatcher = TestCoroutineDispatcher()
    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule()
    private lateinit var viewModel: CharacterDetailViewModel

    @Mock
    lateinit var repository: RepositoryImpl

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = CharacterDetailViewModel(repository)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `getCharacterDetail with result`()= runBlocking{
        whenever(repository.getCharacterDetail(1)).thenReturn(CharacterDetailModel(id=1, name = "test"))
        viewModel.getCharacterDetail(1)
        viewModel.characterDetail.asLiveData().observeForever {
            assertEquals(UiState.Success(CharacterDetailModel(id=1, name = "test")),it)
        }
    }

    @Test
    fun `getCharacterDetail with error`()= runBlocking{
        whenever(repository.getCharacterDetail(2)).doAnswer { throw UnknownHostException() }
        viewModel.getCharacterDetail(2)
        viewModel.characterDetail.asLiveData().observeForever {
            assertEquals(UiState.Error("Error -> Unable to resolve host \"legacy--api.herokuapp.com\": No address associated with hostname"),it)
        }
    }

    @Test
    fun saveToFavorite()= runBlocking{
        var list: MutableList<CharacterDetailModel> = mutableListOf()
        whenever(repository.saveFavoriteIntoDb(
            CharacterDetailModel(id = 11, name = "Sample11")))
            .then { list.add(CharacterDetailModel(id = 11, name = "Sample11")) }
        viewModel.saveFavoriteToDb(CharacterDetailModel(id = 11, name = "Sample11"))
        assertEquals(listOf(CharacterDetailModel(id = 11, name = "Sample11")),list)
    }

}