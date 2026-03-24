package com.example.data.di

import com.example.data.repository.CharactersRepositoryImpl
import com.example.data.repository.EpisodesRepositoryImpl
import com.example.domain.repository.CharactersRepository
import com.example.domain.repository.EpisodesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindCharactersRepository(
        impl: CharactersRepositoryImpl
    ): CharactersRepository

    @Binds
    fun bindEpisodesRepository(
        impl: EpisodesRepositoryImpl
    ): EpisodesRepository

    companion object {
        @Provides
        @Singleton
        fun provideCoroutineScope(): CoroutineScope {
            return CoroutineScope(Dispatchers.Default)
        }
    }
}