package com.example.pruebaceiba.adapters;

import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebaceiba.R
import com.example.pruebaceiba.model.UserPost
import kotlinx.android.synthetic.main.post_list_item.view.*

class RecyclerViewPostsAdapter : RecyclerView.Adapter<RecyclerViewPostsAdapter.MyViewHolder>() {

    var items = ArrayList<UserPost>()

    fun setListData(data: ArrayList<UserPost>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class MyViewHolder (view :View) : RecyclerView.ViewHolder(view){

        val titulo = view.cv_tv_titulo_posts
        val descripcion = view.cv_tv_descripcion_posts

        fun bind(userPost: UserPost){
            titulo.text = userPost.title
            descripcion.text = userPost.body

        }

    }





}
