package ru.akumakeito.githubusers.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.akumakeito.githubusers.data.dao.UsersDao
import ru.akumakeito.githubusers.data.model.UserEntity
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
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = usersDao::pagingSource,
        remoteMediator = remoteMediator
    ).flow
        .map {it.map(UserEntity::fromEntityToUser)}

    override suspend fun getUserList(): List<User> = TODO()
    override suspend fun getUserListSince(sinceId: Int): List<User> = TODO()

    override suspend fun getUserByUsername(username: String): User = TODO()
}