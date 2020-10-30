package com.example.pruebaceiba.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pruebaceiba.utils.DBState
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
    val listUsers = ArrayList<User>()

    init {
        recyclerListData = MutableLiveData()
    }

    fun getRecyclerListDataObserver() : MutableLiveData<ArrayList<User>>{
        return recyclerListData
    }

    fun MakeApiCall( context : Context , progress_Bar : View){
        val database = AppDatabase.getDatabase(context)

        //if (database.users().getSizeUsers()!=DBState.DBsize){
            val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
            var call = retroInstance.getUsersFromAPI()
            call.enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                    if (response.isSuccessful){
                        DBState.DBsize = response.body()!!.size
                        CoroutineScope(Dispatchers.IO).launch{
                            for (usuarios in response.body()!!)
                            {
                                database.users().insertAll(usuarios)
                                listUsers.add(database.users().get(usuarios.id))
                            }
                            recyclerListData.postValue(listUsers)
                        }
                        //recyclerListData.postValue(response.body()!!)
                        //recyclerListData.postValue(listUsers)
                    }else{
                        recyclerListData.postValue((null))
                    }
                }
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    recyclerListData.postValue(null)
                }
            })
       /* }else{
            //listUsers.add(database.users().get(usuarios.id))
            Toast.makeText(context, "offile y db llena", Toast.LENGTH_SHORT)
        }*/


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

