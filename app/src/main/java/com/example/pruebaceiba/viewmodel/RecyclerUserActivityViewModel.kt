package com.example.pruebaceiba.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pruebaceiba.MainActivity
import com.example.pruebaceiba.Utils.DBState
import com.example.pruebaceiba.database.AppDatabase
import com.example.pruebaceiba.model.User
import com.example.pruebaceiba.network.RetroInstance
import com.example.pruebaceiba.network.RetroService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerUserActivityViewModel : ViewModel() {

    lateinit var recyclerListData : MutableLiveData<ArrayList<User>>

    init {
        recyclerListData = MutableLiveData()
    }

    fun getRecyclerListDataObserver() : MutableLiveData<ArrayList<User>>{
        return recyclerListData
    }

    fun MakeApiCall( context : Context ){
        val database = AppDatabase.getDatabase(context)
        //val listUsers = ArrayList<User>()

            val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
            var call = retroInstance.getUsersFromAPI()
            call.enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                    if (response.isSuccessful){
                        //DBState.DBsize = response.body()!!.size
                        CoroutineScope(Dispatchers.IO).launch{
                            for (usuarios in response.body()!!)
                            {
                                database.users().insertAll(usuarios)
                                //listUsers.add(database.users().get(usuarios.id))
                            }
                        }
                        recyclerListData.postValue(response.body()!!)
                    }else{
                        recyclerListData.postValue((null))
                    }
                }
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    recyclerListData.postValue(null)
                }
            })
    }

    /*fun ShowDBinfo( owner : LifecycleOwner){

        val database = AppDatabase.getDatabase(this)
        val listUsers = ArrayList<User>()
        CoroutineScope(Dispatchers.IO).launch{
            database.users().getAll().observe(owner, Observer{
                if (it!=null){
                    val usersIterator = it.iterator()
                    while (usersIterator.hasNext()) {
                        listUsers.add(usersIterator.next())
                    }
                    recyclerListData.postValue(listUsers)
                }

            })

        }

    }*/

}

