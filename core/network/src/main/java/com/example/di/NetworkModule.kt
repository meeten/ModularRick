package com.example.di

import com.example.network.ApiFactory
import com.example.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun bindApiService(): ApiService {
        return ApiFactory.apiService
    }
}