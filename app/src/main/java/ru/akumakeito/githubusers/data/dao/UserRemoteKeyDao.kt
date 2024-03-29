package ru.akumakeito.githubusers.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.akumakeito.githubusers.data.model.UserRemoteKeyEntity

@Dao
interface UserRemoteKeyDao {
    @Query("SELECT COUNT(*) == 0 FROM UserRemoteKeyEntity")
    suspend fun isEmpty() : Boolean

    @Query("SELECT MAX(sinceId) FROM UserRemoteKeyEntity")
    suspend fun max() : Int?

    @Query("SELECT MIN(sinceId) FROM UserRemoteKeyEntity")
    suspend fun min() :Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key : UserRemoteKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys : List<UserRemoteKeyEntity>)

    @Query("DELETE FROM UserRemoteKeyEntity")
    suspend fun removeAll()


}