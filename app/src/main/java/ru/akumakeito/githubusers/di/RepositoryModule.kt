package ru.akumakeito.githubusers.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.akumakeito.githubusers.data.repository.UserRepositoryImpl
import ru.akumakeito.githubusers.domain.repository.UserRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository
}