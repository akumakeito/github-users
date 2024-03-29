package ru.akumakeito.githubusers.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.akumakeito.githubusers.domain.model.User


interface GithubUsersApiService {

    @GET("users")
    suspend fun getUserList(): List<User>

    @GET("users/{username}")
    suspend fun getUserByUsername(@Path("username") username : String): User
}