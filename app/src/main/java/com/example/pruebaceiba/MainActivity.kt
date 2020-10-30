package com.example.pruebaceiba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebaceiba.utils.DBState
import com.example.pruebaceiba.adapters.RecyclerViewUsersAdapter
import com.example.pruebaceiba.database.AppDatabase
import com.example.pruebaceiba.model.User
import com.example.pruebaceiba.viewmodel.RecyclerUserActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

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

        val viewModel = ViewModelProvider(this).get(RecyclerUserActivityViewModel::class.java)
        val database = AppDatabase.getDatabase(this)

        Thread({
            this@MainActivity.runOnUiThread({
                progress_Bar.visibility = View.VISIBLE
            })
            try {
                var i=0
                while(database.users().getSizeUsers()!=DBState.DBsize){
                    i++
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            this@MainActivity.runOnUiThread({
                progress_Bar.visibility = View.GONE
            })
        }).start()

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


    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            newText.toLowerCase(Locale.ENGLISH)
            recyclerViewUsersAdapter.filter(newText, baseContext)
        }
        return false
    }

    fun initListener(){
        val busqueda = et_buscarusuario
        busqueda.setOnQueryTextListener(this)
    }
}