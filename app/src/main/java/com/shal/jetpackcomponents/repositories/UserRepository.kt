package com.shal.jetpackcomponents.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shal.jetpackcomponents.api.AppExecutors
import com.shal.jetpackcomponents.api.NetworkBoundResource
import com.shal.jetpackcomponents.api.Resource
import com.shal.jetpackcomponents.database.UsersDao
import com.shal.jetpackcomponents.models.User
import com.shal.jetpackcomponents.retrofit.IServices
import com.shal.jetpackcomponents.retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserRepository(private val appExecutors: AppExecutors, private val userDao: UsersDao) {
    
    fun getUsersFromAPi() : LiveData<List<User>>{
        var liveData = MutableLiveData<List<User>>()
        val getUsersRequest = RetrofitBuilder.getRetrofitBilder(IServices::class.java)
        val getUsers = getUsersRequest.getUsers()
        CoroutineScope(Dispatchers.IO).launch {
            liveData.postValue(getUsers.execute().body())
        }
        return liveData
    }

    fun loadRepos(): LiveData<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, List<User>>() {

            override fun saveCallResult(item: List<User>) {
                userDao.saveUsers(item)
            }

            override fun shouldFetch(data: List<User>?): Boolean {
                return data?.size == 0
            }

            override fun loadFromDb(): LiveData<List<User>> {
                return userDao.getUsers()
            }

            override fun createCall(): LiveData<List<User>> {
                return getUsersFromAPi()
            }

        }.asLiveData()
    }

    /*fun getUsers() {
        val getUsersRequest = RetrofitBuilder.getRetrofitBilder(IServices::class.java)
        val getUsers = getUsersRequest.getUsers()
        getUsers.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val responseString = response.body()
                print(responseString)
                    responseString?.let {
                        saveUsersInLocalDb(it)
                    }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                print(t.message)
            }
        })

    }

    fun saveUsersInLocalDb(users: List<User>) {
        CoroutineScope(IO).launch {
            userDao.saveUsers(users)
        }
    }*/

}