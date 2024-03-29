package ru.akumakeito.githubusers.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.akumakeito.githubusers.data.dto.UserResponse
import ru.akumakeito.githubusers.domain.model.User


interface GithubUsersApiService {

    @GET("users")
    suspend fun getUserList(): Response<List<UserResponse>>

    @GET("users/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): Response<UserResponse>

    @GET("users")
    suspend fun getUserListSince(@Query("since") sinceId: Int):  Response<List<UserResponse>>

}