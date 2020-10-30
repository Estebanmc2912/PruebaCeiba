package com.example.pruebaceiba

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebaceiba.adapters.RecyclerViewPostsAdapter
import com.example.pruebaceiba.model.UserPost
import com.example.pruebaceiba.viewmodel.RecyclerPostsActivityViewModel
import kotlinx.android.synthetic.main.activity_post.*

class PostsActivity : AppCompatActivity() {

    lateinit var recyclerViewPostsAdapter: RecyclerViewPostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        initRecyclerView()
        createData()
    }

    private fun initRecyclerView(){

        rv_posts.apply {
            layoutManager = LinearLayoutManager(this@PostsActivity)
            recyclerViewPostsAdapter = RecyclerViewPostsAdapter()
            adapter = recyclerViewPostsAdapter
        }
    }

    fun createData(){

        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        val id  = intent.getStringExtra("id")?.toInt()

        cv_tv_nombre_post.text = name
        cv_tv_telefono_post.text = phone
        cv_tv_correo_post.text = email

        val viewModel = ViewModelProvider(this).get(RecyclerPostsActivityViewModel::class.java)


        viewModel.getRecyclerListDataObserver()
                .observe(this, Observer<ArrayList<UserPost>> {
                    if (it != null) {
                        recyclerViewPostsAdapter.setListData(it)
                        recyclerViewPostsAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                                this@PostsActivity,
                                "Error in getting data from api",
                                Toast.LENGTH_LONG
                        ).show()
                    }
                })

        if (id != null) {
            viewModel.MakeDBCallPosts(this, id)
        }


    }




}