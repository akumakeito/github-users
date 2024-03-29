package ru.akumakeito.githubusers.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.akumakeito.githubusers.data.model.UserEntity
import ru.akumakeito.githubusers.data.model.UserRemoteKeyEntity

@Database(entities = [UserEntity::class, UserRemoteKeyEntity::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {

    abstract fun usersDao(): UsersDao

    abstract fun userRemoteKeyDao(): UserRemoteKeyDao

}