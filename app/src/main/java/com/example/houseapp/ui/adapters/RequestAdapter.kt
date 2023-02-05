package com.example.houseapp.ui.adapters

import android.annotation.SuppressLint
import com.example.houseapp.R
import com.example.houseapp.data.models.UserRequest
import com.example.houseapp.databinding.ListViewItemRequestBinding

class RequestDiffCallback : ItemDiffCallback<UserRequest>() {
    override fun areItemsTheSame(oldItem: UserRequest, newItem: UserRequest): Boolean =
        oldItem.requestId == newItem.requestId
    override fun areContentsTheSame(oldItem: UserRequest, newItem: UserRequest): Boolean =
        oldItem == newItem
}

class RequestAdapter(private val callback: ItemClickListener<UserRequest>) :
    BaseRecyclerAdapter<UserRequest, ListViewItemRequestBinding>(
        R.layout.list_view_item_request,
        RequestDiffCallback()
        ) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BaseViewHolder<ListViewItemRequestBinding>, position: Int) {
        holder.viewDataBinding.also {
            it.request = getItem(position)
            it.requestCallback = callback
        }
    }
}