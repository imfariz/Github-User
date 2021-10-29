package com.dicoding.submission1_fundamentalandroid.models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission1_fundamentalandroid.database.User
import com.dicoding.submission1_fundamentalandroid.repository.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<User>> = mUserRepository.getAllUsers()

    fun check(login: String): LiveData<Int> = mUserRepository.checkUser(login)

    fun insert(user: User) {
        mUserRepository.insert(user)
    }

    fun delete(login: String) {
        mUserRepository.delete(login)
    }
}