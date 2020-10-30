package com.example.pruebaceiba.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pruebaceiba.database.AppDatabase
import com.example.pruebaceiba.model.UserPost
import com.example.pruebaceiba.network.RetroInstance
import com.example.pruebaceiba.network.RetroService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecyclerPostsActivityViewModel : ViewModel(){


    lateinit var recyclerListData : MutableLiveData<ArrayList<UserPost>>
    val listPosts = ArrayList<UserPost>()

    init {
        recyclerListData = MutableLiveData()
    }

    fun getRecyclerListDataObserver() : MutableLiveData<ArrayList<UserPost>> {
        return recyclerListData
    }

    fun MakeDBCallPosts(context : Context, index : Int){
        val database = AppDatabase.getDatabase(context)
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        var call = retroInstance.getUserPostFromAPI(index.toString())
        call.enqueue(object : Callback<ArrayList<UserPost>> {
            override fun onResponse(call: Call<ArrayList<UserPost>>, response: Response<ArrayList<UserPost>>) {
                if (response.isSuccessful){
                    //DBState.DBsize = response.body()!!.size
                    CoroutineScope(Dispatchers.IO).launch{
                        for (posts in response.body()!!)
                        {
                            database.userPosts().insertAll(posts)
                            listPosts.add(database.userPosts().get(posts.id))
                        }
                        recyclerListData.postValue(listPosts)
                    }
                    //recyclerListData.postValue(response.body()!!)
                    //recyclerListData.postValue(listUsers)
                }else{
                    recyclerListData.postValue((null))
                }
            }
            override fun onFailure(call: Call<ArrayList<UserPost>>, t: Throwable) {
                recyclerListData.postValue(null)
            }
        })
        /* }else{
             //listUsers.add(database.users().get(usuarios.id))
             Toast.makeText(context, "offile y db llena", Toast.LENGTH_SHORT)
         }*/


    }

}