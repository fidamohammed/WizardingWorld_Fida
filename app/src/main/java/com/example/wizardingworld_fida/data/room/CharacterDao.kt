package com.example.wizardingworld_fida.data.room

import androidx.room.*
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteInDb(characterDetailModel: CharacterDetailModel)

    @Query("Select * from Character")
    fun getFavoritesFromDb(): Flow<List<CharacterDetailModel>>

    @Delete
    suspend fun deleteFavorite(characterDetailModel: CharacterDetailModel)

    @Query("Select * from Character where id==:id")
    fun checkFavoriteExist(id: Int):Flow<CharacterDetailModel>
}