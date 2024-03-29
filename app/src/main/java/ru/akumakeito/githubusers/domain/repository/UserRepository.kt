package ru.akumakeito.githubusers.domain.repository


import ru.akumakeito.githubusers.domain.model.User

interface UserRepository {

    suspend fun getUserList(): List<User>

    suspend fun getUserByUsername(username : String): User
}

