package com.example.arogyasahayalocal.repository

import com.example.arogyasahayalocal.data.local.dao.UserDao
import com.example.arogyasahayalocal.data.local.entities.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User): Long = userDao.insertUser(user)
    suspend fun login(username: String): User? = userDao.getUserByUsername(username)
    fun getUserById(userId: Int): Flow<User?> = userDao.getUserById(userId)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
}
