package ru.akumakeito.githubusers.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ru.akumakeito.githubusers.data.dao.UsersDao
import ru.akumakeito.githubusers.data.model.UserEntity
import ru.akumakeito.githubusers.data.model.toEntity
import ru.akumakeito.githubusers.network.GithubUsersApiService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val apiService: GithubUsersApiService,
    private val usersDao: UsersDao,
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
            val lastId = usersDao.max()

            Log.d("UserRemoteMediator", "last id: $lastId")




            val response = if (lastId == null) {
                apiService.getUserList()
            } else {
                apiService.getUserListSince(lastId)
            }


            val body = response.body() ?: throw Exception("response body is null")

            Log.d("UserRemoteMediator", "body: $body")


            usersDao.insert(body.toEntity())

            val endOfPagination = body.isEmpty()
            return MediatorResult.Success(
                endOfPaginationReached = endOfPagination
            )

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}