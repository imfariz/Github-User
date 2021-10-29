package com.dicoding.submission1_fundamentalandroid.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.submission1_fundamentalandroid.database.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Query("DELETE FROM user WHERE login = :login")
    fun delete(login :String?)

    @Query("SELECT * from user ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT COUNT(*) FROM user WHERE login = :login")
    fun checkUser(login: String): LiveData<Int>
}
