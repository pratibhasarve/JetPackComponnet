package com.shal.jetpackcomponents.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shal.jetpackcomponents.database.UsersDao
import com.shal.jetpackcomponents.database.UsersDatabase
import com.shal.jetpackcomponents.repositories.UserRepository
import com.shal.jetpackcomponents.retrofit.RetrofitBuilder

class UsersViewModel(val usersRepository: UserRepository) : ViewModel() {

    var usersList = usersRepository.loadRepos()

    class UsersViewModelFactory(private val repository: UserRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UsersViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}