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

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun getUserByID(id : Int) : UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users : List<UserEntity>)


    @Query("SELECT * FROM UserEntity ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, UserEntity>

    @Query("SELECT MAX(id) FROM UserEntity")
    suspend fun max() : Int?
}