package com.shal.jetpackcomponents.retrofit

import com.shal.jetpackcomponents.models.User
import retrofit2.Call
import retrofit2.http.GET

interface IServices {

    @GET("1/posts")
    fun getUsers(): Call<List<User>>
}