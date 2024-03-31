package ru.akumakeito.githubusers.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.akumakeito.githubusers.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 5, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {

    abstract fun usersDao(): UsersDao


}