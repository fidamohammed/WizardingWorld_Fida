package com.example.wizardingworld_fida.data.repository

import com.example.wizardingworld_fida.data.api.NetworkDetails
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.data.room.CharacterDao
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class RepositoryImplTest{

    lateinit var repositoryImpl: RepositoryImpl

    @Mock
    lateinit var networkDetails: NetworkDetails

    @Mock
    lateinit var characterDao: CharacterDao

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        repositoryImpl = RepositoryImpl(networkDetails, characterDao)
    }

    @Test
    fun getCharacters()= runBlocking{
        whenever(networkDetails.getCharacters(1))
            .thenReturn(arrayListOf(
                CharacterItemModel(id = 1,name = "Test1"),
                CharacterItemModel(id = 2, name = "Test2")
            ))
        val result = repositoryImpl.getCharacters(1)
        assertEquals(arrayListOf(
            CharacterItemModel(id = 1,name = "Test1"),
            CharacterItemModel(id = 2, name = "Test2")
        ),result)
    }

    @Test
    fun getCharacterDetail() = runBlocking{
        whenever(networkDetails.getCharacterDetail(1))
            .thenReturn(
                CharacterDetailModel(id = 1,name = "Test1"),
            )
        val result = repositoryImpl.getCharacterDetail(1)
        assertEquals(CharacterDetailModel(id = 1,name = "Test1"),result)
    }

    @Test
    fun getFavorites(){
        val testdata= flowOf(listOf(
            CharacterDetailModel(id = 1, name = "Test")
        ))
        whenever(characterDao.getFavoritesFromDb()).thenReturn(testdata)
        val result = repositoryImpl.getFavorites()
        assertEquals(testdata,result)
    }



}