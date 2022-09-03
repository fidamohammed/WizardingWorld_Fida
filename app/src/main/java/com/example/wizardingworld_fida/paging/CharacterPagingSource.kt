package com.example.wizardingworld_fida.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wizardingworld_fida.data.model.CharacterItemModel
import com.example.wizardingworld_fida.data.repository.Repository
import com.example.wizardingworld_fida.data.repository.RepositoryImpl


class CharacterPagingSource (val repositoryImpl: RepositoryImpl): PagingSource<Int, CharacterItemModel>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterItemModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterItemModel> {
        return try{
            val nextPage: Int = params.key ?: 1
            val result = repositoryImpl.getCharacters(nextPage)

            LoadResult.Page(data= result,
                prevKey = null,
                nextKey = nextPage.plus(1))
        }
        catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}