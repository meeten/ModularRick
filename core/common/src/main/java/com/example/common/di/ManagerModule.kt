package com.example.common.di

import com.example.common.exception.GlobalExceptionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {

    @Provides
    fun provideGlobalExceptionManager(): GlobalExceptionManager {
        return GlobalExceptionManager
    }
}