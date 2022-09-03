package com.example.wizardingworld_fida.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wizardingworld_fida.data.model.CharacterDetailModel

@Database(entities = [CharacterDetailModel::class], version = 1, exportSchema = false)
@TypeConverters(CharacterTypeConverter::class)
abstract class WizardDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}