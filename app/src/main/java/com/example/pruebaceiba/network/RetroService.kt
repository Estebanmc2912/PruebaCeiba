package com.example.pruebaceiba.network

import com.example.pruebaceiba.model.User
import com.example.pruebaceiba.model.UserPost
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface RetroService {

    @GET("/users") //GET “/users”
    fun getUsersFromAPI(): Call<ArrayList<User>>

    @GET("/posts") //GET “/users”
    fun getUsersPostFromAPI(): Call<ArrayList<UserPost>>

    @GET("/posts") //GET “GET “/posts?userId=1”
    fun getUserPostFromAPI(@Query("userId") query: String): Call<ArrayList<UserPost>>



}