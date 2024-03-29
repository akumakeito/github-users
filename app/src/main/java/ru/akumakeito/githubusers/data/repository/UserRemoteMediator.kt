package ru.akumakeito.githubusers.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ru.akumakeito.githubusers.data.dao.UserRemoteKeyDao
import ru.akumakeito.githubusers.data.dao.UsersDao
import ru.akumakeito.githubusers.data.model.UserEntity
import ru.akumakeito.githubusers.network.GithubUsersApiService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val apiService: GithubUsersApiService,
    private val userRemoteKeyDao: UserRemoteKeyDao,
    private val usersDao: UsersDao
) : RemoteMediator<Int, UserEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        TODO("Not yet implemented")
    }
}