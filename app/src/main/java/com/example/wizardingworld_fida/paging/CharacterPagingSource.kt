package com.example.wizardingworld_fida.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.data.repository.Repository
import com.example.wizardingworld_fida.data.repository.RepositoryImpl
import javax.inject.Inject


class CharacterPagingSource @Inject constructor(val repository: Repository): PagingSource<Int, CharacterItemModel>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterItemModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, CharacterItemModel> {
        return try{
            val nextPage: Int = params.key ?: 1
            val result = repository.getCharacters(nextPage)

            PagingSource.LoadResult.Page(data= result,
                prevKey = null,
                nextKey = nextPage.plus(1))
        }
        catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}