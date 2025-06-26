package com.example.bookandfriend.di

import com.example.bookandfriend.data.repository.LibraryRepositoryImpl
import com.example.bookandfriend.data.repository.SearchRepositoryImpl
import com.example.bookandfriend.domain.repository.LibraryRepository
import com.example.bookandfriend.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindLibraryRepository(libraryRepositoryImpl: LibraryRepositoryImpl): LibraryRepository


}