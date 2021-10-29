package com.dicoding.submission1_fundamentalandroid.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.submission1_fundamentalandroid.database.User
import com.dicoding.submission1_fundamentalandroid.database.UserDao
import com.dicoding.submission1_fundamentalandroid.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<User>> = mUserDao.getAllUsers()

    fun checkUser(login: String): LiveData<Int> = mUserDao.checkUser(login)

    fun insert(user: User) {
        executorService.execute { mUserDao.insert(user) }
    }

    fun delete(user: String) {
        executorService.execute { mUserDao.delete(user) }
    }
}