package com.w36495.senty.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w36495.senty.R
import com.w36495.senty.data.domain.Friend
import com.w36495.senty.databinding.FriendListItemBinding
import com.w36495.senty.view.FriendSelectListener

class FriendAdapter(private val context: Context, private val friendSelectListener: FriendSelectListener) : RecyclerView.Adapter<FriendAdapter.FriendHolder>() {

    private var friendList = arrayListOf<Friend>()

    inner class FriendHolder(private val binding: FriendListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(friend: Friend) {
            binding.friendListName.text = friend.name
            binding.friendListPhone.text = friend.phone
            binding.friendItemImg.setImageResource(R.drawable.ic_launcher_background)

            itemView.setOnClickListener {
                friendSelectListener.onFriendInfoClicked(friend, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_list_item, parent, false)
        return FriendHolder(FriendListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        holder.bind(friendList[position])
    }

    override fun getItemCount(): Int = friendList.size

    fun setFriendList(friendList: ArrayList<Friend>) {
        this.friendList = friendList
        notifyDataSetChanged()
    }
}