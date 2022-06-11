package com.example.demoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoapp.R
import com.example.demoapp.model.User

class UserListAdapter(private var usersList: List<User.Datum>) :
    RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewEmail: AppCompatTextView = view.findViewById(R.id.textViewEmail)
        var imageViewProfile: AppCompatImageView = view.findViewById(R.id.imageViewProfile)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_user_item, parent, false)
        return MyViewHolder(itemView)
    }

    private fun getUsers(position: Int): User.Datum {
        return usersList[position]
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userData = getUsers(position)
        userData.apply {
            holder.textViewEmail.text = email
            Glide.with(holder.itemView.context)
                .load(avatar)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(holder.imageViewProfile)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}