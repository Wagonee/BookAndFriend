package com.example.bookandfriend.di

import com.example.bookandfriend.data.mappers.AndroidBookMapperResourceProvider
import com.example.bookandfriend.data.mappers.BookMapperResourceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {
    @Binds
    @Singleton
    abstract fun bindBookMapperResourceProvider(
        androidBookMapperResourceProvider: AndroidBookMapperResourceProvider
    ): BookMapperResourceProvider
}