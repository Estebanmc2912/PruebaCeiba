package com.example.pruebaceiba

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebaceiba.model.User
import kotlinx.android.synthetic.main.user_list_item.view.*
import java.util.stream.Collectors

class RecyclerViewUsersAdapter : RecyclerView.Adapter<RecyclerViewUsersAdapter.MyViewHolder>() {

    var items = ArrayList<User>()
    var originalitems = ArrayList<User>()

    fun setListData(data: ArrayList<User>){
        this.items = data
        originalitems.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewUsersAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun filter(strSearch : String, context : Context) {
        if (strSearch.length==0){
            items.clear()
            items.addAll(originalitems)
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                items.clear()
                val collect = originalitems.stream()
                    .filter { i -> i.name.toLowerCase().contains(strSearch)  }
                    .collect(Collectors.toList())
                items.addAll(collect)

                if(items.size==0){
                    Toast.makeText(context, "List is empty", Toast.LENGTH_SHORT)
                      .show()
                }

            }
        }

        notifyDataSetChanged()
    }

    class MyViewHolder (view : View) : RecyclerView.ViewHolder(view){

        val nombre = view.cv_tv_nombre
        val telefono = view.cv_tv_telefono
        val correo = view.cv_tv_correo

        fun bind(user: User){
            nombre.text = user.name
            telefono.text = user.phone
            correo.text = user.email
        }

    }

}

