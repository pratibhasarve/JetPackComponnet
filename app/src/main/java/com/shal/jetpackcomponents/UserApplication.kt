package com.shal.jetpackcomponents

import android.app.Application
import com.shal.jetpackcomponents.api.AppExecutors
import com.shal.jetpackcomponents.database.UsersDatabase
import com.shal.jetpackcomponents.repositories.UserRepository
import com.shal.jetpackcomponents.retrofit.IServices
import com.shal.jetpackcomponents.retrofit.RetrofitBuilder

class UserApplication: Application() {

    val database by lazy { UsersDatabase.getDatabase(this) }
    val repository by lazy { UserRepository(AppExecutors(), database.userDao()) }
}