package com.example.lab6

import androidx.room.*
import androidx.room.Dao

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM User WHERE login == :login")
    suspend fun getUserByLogin(login: String): User?

    @Query("SELECT * FROM User WHERE user_id == :id")
    suspend fun getUserById(id: Long): User?
}