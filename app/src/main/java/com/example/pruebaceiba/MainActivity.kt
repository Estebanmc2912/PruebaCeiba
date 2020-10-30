package com.example.pruebaceiba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebaceiba.utils.DBState
import com.example.pruebaceiba.adapters.RecyclerViewUsersAdapter
import com.example.pruebaceiba.database.AppDatabase
import com.example.pruebaceiba.model.User
import com.example.pruebaceiba.viewmodel.RecyclerUserActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

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
        val database = AppDatabase.getDatabase(this)


        Thread(Runnable {
            // dummy thread mimicking some operation whose progress cannot be tracked

            // display the indefinite progressbar
            this@MainActivity.runOnUiThread(java.lang.Runnable {
                progress_Bar.visibility = View.VISIBLE
            })

            // performing some dummy time taking operation
            try {
                var i=0;
                while(database.users().getSizeUsers()!=DBState.DBsize){
                    i++
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            // when the task is completed, make progressBar gone
            this@MainActivity.runOnUiThread(java.lang.Runnable {
                progress_Bar.visibility = View.GONE
            })
        }).start()


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
                viewModel.MakeApiCall(this, progress_Bar)
                /*if (DBsize == dbsize) {
                    Toast.makeText(this,"offline",Toast.LENGTH_SHORT)
                    viewModel.ShowDBinfo(this,this)
                }*/

           // })


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