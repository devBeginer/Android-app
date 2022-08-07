package com.example.lab6

class Repository(private val userDao: UserDao) {
    suspend fun getUserById(userId: Long): User?{
        return userDao.getUserById(userId)
    }

    suspend fun getUserByLogin(login: String): User?{
        return userDao.getUserByLogin(login)
    }

    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }
}