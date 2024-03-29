package ru.akumakeito.githubusers.domain.model

data class User(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val name : String,
    val company : String?,
    val email : String?,
    val followers : Int,
    val following : Int,
    val createdAt : String,
)
