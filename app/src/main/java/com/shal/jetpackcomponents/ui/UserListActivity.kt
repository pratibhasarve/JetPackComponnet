package com.shal.jetpackcomponents.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shal.jetpackcomponents.R
import com.shal.jetpackcomponents.UserApplication
import com.shal.jetpackcomponents.viewmodels.UsersViewModel

class UserListActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    val adapter = UsersListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        loadView()
        loadData()
    }

    private fun loadView() {
        recyclerView = findViewById(R.id.userList)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
    }

    private fun loadData() {

        val userViewModel: UsersViewModel by viewModels(){
            UsersViewModel.UsersViewModelFactory((application as UserApplication).repository)
        }

        userViewModel.usersList?.observe(this, {
            it.data?.let { it1 -> adapter.submitList(it1) }
        })
    }
}