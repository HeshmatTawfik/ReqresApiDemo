package com.heshmat.reqresapidemo.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.heshmat.reqresapidemo.R
import com.heshmat.reqresapidemo.model.User
import kotlinx.android.synthetic.main.user_item_layout.view.*

class UsersAdapter(private val userArrList: ArrayList<User>, private val listener: ItemClickListener) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_item_layout, parent, false), this.listener
        )

    override fun getItemCount(): Int = userArrList.size
    fun addData(list: List<User>) {
        userArrList.addAll(list)
    }

    fun clearData() {
        userArrList.clear()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userArrList[position])
    }

    class ViewHolder(itemView: View, private val listener: ItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.setOnClickListener { listener.onItemClickListener(user) }
            itemView.nameTv.text = "${user.firstName} ${user.lastName}"
            itemView.emailTv.text = user.email
            Glide.with(itemView.avatarIv.context)
                .load(user.avatar)
                .circleCrop()
                .placeholder(R.drawable.avatar_place_holder)
                .into(itemView.avatarIv)

        }
    }
}

interface ItemClickListener {
    fun onItemClickListener(item: User)
}