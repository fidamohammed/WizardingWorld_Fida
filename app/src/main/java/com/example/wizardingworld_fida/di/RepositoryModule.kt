package com.example.wizardingworld_fida.di

import com.example.wizardingworld_fida.data.repository.Repository
import com.example.wizardingworld_fida.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun getRepository(repositoryImpl: RepositoryImpl): Repository
}