package com.example.pruebaceiba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebaceiba.database.AppDatabase
import com.example.pruebaceiba.model.User
import com.example.pruebaceiba.model.UserPost
import com.example.pruebaceiba.network.RetroInstance
import com.example.pruebaceiba.network.RetroService
import com.example.pruebaceiba.viewmodel.RecyclerUserActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener{

    lateinit var recyclerViewUsersAdapter: RecyclerViewUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        createData()
        initListener()
    }

    private fun initRecyclerView(){

        rv_activity_main.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewUsersAdapter = RecyclerViewUsersAdapter()
            adapter = recyclerViewUsersAdapter
        }
    }

    fun createData(){

        val viewModel = ViewModelProviders.of(this).get(RecyclerUserActivityViewModel::class.java)
        //val database = AppDatabase.getDatabase(this)
         //   database.users().getAll().observe(this, Observer {
                viewModel.getRecyclerListDataObserver()
                    .observe(this, Observer<ArrayList<User>> {
                        if (it != null) {
                            recyclerViewUsersAdapter.setListData(it)
                            recyclerViewUsersAdapter.notifyDataSetChanged()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Error in getting data from api",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                    })
                viewModel.MakeApiCall(this)
                /*if (DBsize == dbsize) {
                    Toast.makeText(this,"offline",Toast.LENGTH_SHORT)
                    viewModel.ShowDBinfo(this,this)
                }*/

           // })

        /*var callPosts = retroInstance.getUsersPostFromAPI()
        callPosts.enqueue(object : Callback<ArrayList<UserPost>>{
            override fun onResponse(call: Call<ArrayList<UserPost>>, response: Response<ArrayList<UserPost>>) {
                if (response.isSuccessful){

                    //recyclerViewAdapter.setListData( response.body()!! )
                    recyclerViewUsersAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<UserPost>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error in getting user posts from api", Toast.LENGTH_LONG)
                        .show()
            }

        })*/

    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            newText.toLowerCase()
            recyclerViewUsersAdapter.filter(newText, baseContext)
        }
        return false
    }

    fun initListener(){
        var busqueda = et_buscarusuario
        busqueda.setOnQueryTextListener(this)
    }


}