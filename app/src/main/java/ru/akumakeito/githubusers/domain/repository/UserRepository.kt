package ru.akumakeito.githubusers.domain.repository


import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.akumakeito.githubusers.domain.model.User

interface UserRepository {

    val data : Flow<PagingData<User>>

    suspend fun getUserList(): List<User>
    suspend fun getUserListSince(sinceId : Int): List<User>

    suspend fun getUserByUsername(username : String): User
}

