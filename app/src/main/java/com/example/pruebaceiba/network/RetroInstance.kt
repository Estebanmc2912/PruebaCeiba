package com.example.pruebaceiba.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object{
        val baseURL = "https://jsonplaceholder.typicode.com"
        fun getRetroInstance() : Retrofit{

            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}