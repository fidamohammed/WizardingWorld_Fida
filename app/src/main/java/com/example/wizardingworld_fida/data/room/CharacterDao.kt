package com.example.wizardingworld_fida.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wizardingworld_fida.data.model.CharacterDetailModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteInDb(characterDetailModel: CharacterDetailModel)

    @Query("Select * from Character")
    fun getFavoritesFromDb(): Flow<List<CharacterDetailModel>>
}