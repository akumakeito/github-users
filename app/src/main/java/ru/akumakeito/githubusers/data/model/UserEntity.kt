package ru.akumakeito.githubusers.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.akumakeito.githubusers.data.dto.UserResponse
import ru.akumakeito.githubusers.domain.model.User

@Entity
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,
    val name: String?,
    val company: String?,
    val email: String?,
    val followers: Int,
    val following: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: String?,
) {

    companion object {
        fun fromUserResponseToEntity(userResponse: UserResponse) = UserEntity(
            login = userResponse.login,
            id = userResponse.id,
            avatarUrl = userResponse.avatarUrl,
            name = userResponse.name,
            company = userResponse.company,
            email = userResponse.email,
            followers = userResponse.followers,
            following = userResponse.following,
            createdAt = userResponse.createdAt
        )
    }


    fun fromEntityToUser(): User = User(
        login = login,
        id = id,
        avatarUrl = avatarUrl,
        name = name,
        company = company,
        email = email,
        followers = followers,
        following = following,
        createdAt = createdAt
    )

}

fun List<UserResponse>.toEntity(): List<UserEntity> = map(UserEntity::fromUserResponseToEntity)
fun List<UserEntity>.toUser(): List<User> = map(UserEntity::fromEntityToUser)
