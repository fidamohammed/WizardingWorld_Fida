package com.example.wizardingworld_fida.di

import android.content.Context
import androidx.room.Room
import com.example.wizardingworld_fida.data.room.WizardDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, WizardDatabase::class.java,"WizardDatabase"
    ).build()


    @Provides
    fun provideCharacterDao(database: WizardDatabase) = database.characterDao()
}