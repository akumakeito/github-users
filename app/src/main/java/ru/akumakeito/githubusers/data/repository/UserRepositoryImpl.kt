package ru.akumakeito.githubusers.data.repository

import ru.akumakeito.githubusers.domain.model.User
import ru.akumakeito.githubusers.domain.repository.UserRepository
import ru.akumakeito.githubusers.network.GithubUsersApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
   private val apiService: GithubUsersApiService
)  : UserRepository {
    override suspend fun getUserList(): List<User> = apiService.getUserList()

    override suspend fun getUserByUsername(username: String): User = apiService.getUserByUsername(username)
}