package ru.akumakeito.githubusers.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ru.akumakeito.githubusers.data.dao.AppDb
import ru.akumakeito.githubusers.data.dao.UserRemoteKeyDao
import ru.akumakeito.githubusers.data.dao.UsersDao
import ru.akumakeito.githubusers.data.model.UserEntity
import ru.akumakeito.githubusers.data.model.UserRemoteKeyEntity
import ru.akumakeito.githubusers.network.GithubUsersApiService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val apiService: GithubUsersApiService,
    private val userRemoteKeyDao: UserRemoteKeyDao,
    private val usersDao: UsersDao,
    private val database : AppDb
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun initialize(): InitializeAction = if (usersDao.isEmpty()) {
        InitializeAction.LAUNCH_INITIAL_REFRESH
    } else {
        InitializeAction.SKIP_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        try {
            val response = when (loadType) {
                LoadType.REFRESH -> {
                    val id = userRemoteKeyDao.max()
                    if (id == null) {
                        apiService.getUserList()
                    } else {
                        apiService.getUserListSince(id)
                    }
                }
                LoadType.PREPEND -> {
                    val id = userRemoteKeyDao.min() ?: return MediatorResult.Success(false)
                    apiService.getUserListSince(id)

                }
                LoadType.APPEND -> {
                    val id = userRemoteKeyDao.max() ?: return MediatorResult.Success(false)
                    apiService.getUserListSince(id)
                }
            }

            val body = response.body() ?: throw Exception("response body is null")

            if (body.isEmpty()) {
                return MediatorResult.Success(false)
            }

            database.withTransaction {
                when(loadType) {
                    LoadType.REFRESH -> {
                        if (usersDao.isEmpty()) {
                            userRemoteKeyDao.insert(
                                listOf(
                                    UserRemoteKeyEntity(
                                        keyType = UserRemoteKeyEntity.KeyType.AFTER,
                                        sinceId = body.first().id
                                    ),
                                    UserRemoteKeyEntity(
                                        keyType = UserRemoteKeyEntity.KeyType.BEFORE,
                                        sinceId = body.last().id
                                    )
                                )
                            )
                        } else {
                            userRemoteKeyDao.insert(
                                UserRemoteKeyEntity(
                                    keyType = UserRemoteKeyEntity.KeyType.AFTER,
                                    sinceId = body.first().id
                                )
                            )
                        }

                    }
                    LoadType.PREPEND ->
                        userRemoteKeyDao.insert(
                            UserRemoteKeyEntity(
                                keyType = UserRemoteKeyEntity.KeyType.AFTER,
                                sinceId = body.first().id
                            )
                        )
                    LoadType.APPEND -> {
                        userRemoteKeyDao.insert(
                            UserRemoteKeyEntity(
                                keyType = UserRemoteKeyEntity.KeyType.BEFORE,
                                sinceId = body.last().id
                            )
                        )
                    }
                }
                usersDao.insert(body.map(UserEntity::fromUserResponseToEntity))
            }

            return MediatorResult.Success(body.isNotEmpty())

        } catch (e : Exception) {
            return MediatorResult.Error(e)
        }
    }
}