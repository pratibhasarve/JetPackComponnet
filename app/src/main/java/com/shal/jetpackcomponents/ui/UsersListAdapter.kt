package com.shal.jetpackcomponents.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shal.jetpackcomponents.R
import com.shal.jetpackcomponents.models.User
import com.shal.jetpackcomponents.ui.UsersListAdapter.UsersViewHolder

class UsersListAdapter(private val context: Context) : RecyclerView.Adapter<UsersViewHolder>() {

    var list: List<User> = listOf()

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtUserName = itemView.findViewById<TextView>(R.id.txt_username)
        val txtBody = itemView.findViewById<TextView>(R.id.txt_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.single_user_row, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.txtUserName.text = list[position].title
        holder.txtBody.text = list[position].body
    }

    override fun getItemCount() = list.size

    fun submitList(dataset: List<User>) {
        list = dataset
        notifyDataSetChanged()
    }
}