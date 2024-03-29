package ru.akumakeito.githubusers.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserRemoteKeyEntity(
    @PrimaryKey
    val sinceId: Long
)
