package com.example.data.di

import com.example.data.repository.RickAndMortyRepositoryImpl
import com.example.domain.repository.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindRickAndMortyRepository(
        impl: RickAndMortyRepositoryImpl
    ): RickAndMortyRepository
}