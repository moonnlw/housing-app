package com.example.houseapp.ui.adapters

import android.annotation.SuppressLint
import com.example.houseapp.R
import com.example.houseapp.data.models.User
import com.example.houseapp.databinding.ListViewItemUserBinding

class UserDiffCallback : ItemDiffCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}

class UserAdapter(private val callback: ItemClickListener<User>) :
    BaseRecyclerAdapter<User, ListViewItemUserBinding>(
        R.layout.list_view_item_user,
        UserDiffCallback()
    ) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BaseViewHolder<ListViewItemUserBinding>, position: Int) {
        holder.viewDataBinding.also {
            it.user = getItem(position)
            it.userCallback = callback
        }
    }
}