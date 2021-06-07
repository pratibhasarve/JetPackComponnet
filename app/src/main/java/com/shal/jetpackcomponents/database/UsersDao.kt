package com.shal.jetpackcomponents.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shal.jetpackcomponents.models.User
import java.util.concurrent.Flow

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUsers(users: List<User>)

    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<User>>
}