package ru.akumakeito.githubusers.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.akumakeito.githubusers.data.dao.UsersDao
import ru.akumakeito.githubusers.data.model.UserEntity
import ru.akumakeito.githubusers.data.model.UserEntity.Companion.fromUserResponseToEntity
import ru.akumakeito.githubusers.data.model.toEntity
import ru.akumakeito.githubusers.domain.model.User
import ru.akumakeito.githubusers.domain.repository.UserRepository
import ru.akumakeito.githubusers.network.GithubUsersApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val usersDao: UsersDao,
    remoteMediator: UserRemoteMediator,
    private val apiService: GithubUsersApiService,
)  : UserRepository {

    @OptIn(ExperimentalPagingApi::class)
    override val data: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = usersDao::pagingSource,
        remoteMediator = remoteMediator
    ).flow
        .map {
            it.map(UserEntity::fromEntityToUser)
        }

    override suspend fun getUserList() {
        try {
            val response = apiService.getUserList()
            if (!response.isSuccessful) {
                throw Exception("response is not successful")
            }

            val body = response.body() ?: throw Exception("response body is null")
            if (usersDao.isEmpty()) {
                usersDao.insert(body.toEntity())
            }

//            if  (body.first().id > (userRemoteKeyDao.max() ?: 0)) {
//                usersDao.insert(body.toEntity())
//            }
        } catch (e : Exception) {
            throw e.message?.let { Exception(it) } ?: Exception("Unknown error")
        }
    }
    override suspend fun getUserListSince(sinceId: Int) {
        try {
            val response = apiService.getUserListSince(sinceId)
            if (!response.isSuccessful) {
                throw Exception("response is not successful")
            }
            val body = response.body() ?: throw Exception("response body is null")
            usersDao.insert(body.toEntity())

        } catch (e : Exception) {
            throw e.message?.let { Exception(it) } ?: Exception("Unknown error")
        }
    }

    override suspend fun getUserByUsername(username: String): User {
        try {
            val response = apiService.getUserByUsername(username)
            if (!response.isSuccessful) {
                throw Exception("response is not successful")
            }
            val body = response.body() ?: throw Exception("response body is null")

            Log.d("FragmentUserDetails", "user = ${body}")
            usersDao.insert(fromUserResponseToEntity(body))

            val user = usersDao.getUserByID(body.id).fromEntityToUser()
            Log.d("FragmentUserDetails", "repo returns user = ${user}")

            return usersDao.getUserByID(body.id).fromEntityToUser()

        } catch (e : Exception) {
            throw e.message?.let { Exception(it) } ?: Exception("Unknown error")
        }
    }
}