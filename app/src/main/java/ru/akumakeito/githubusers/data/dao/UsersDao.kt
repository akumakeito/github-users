package ru.akumakeito.githubusers.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.akumakeito.githubusers.data.model.UserEntity

@Dao
interface UsersDao {

    @Query("SELECT COUNT(*) == 0 FROM UserEntity")
    suspend fun isEmpty() : Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts : List<UserEntity>)

    @Query("SELECT * FROM UserEntity ORDER BY id DESC")
    fun pagingSource(): PagingSource<Int, UserEntity>
}