package com.example.houseapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Класс для обработки нажатия на элемент адаптера
 */
class ItemClickListener<T>(val block: (T) -> Unit) {
    fun onClick(item: T) = block(item)
}

abstract class ItemDiffCallback<T : Any> : DiffUtil.ItemCallback<T>()

/**
 * Адаптер применяет data binding к каждому элементу itemList
 */
abstract class BaseRecyclerAdapter<T : Any, B : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    diffCallback: ItemDiffCallback<T>
) : ListAdapter<T, BaseRecyclerAdapter.BaseViewHolder<B>>(diffCallback) {

    class BaseViewHolder<B : ViewDataBinding>(val viewDataBinding: B) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<B> {
        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutResId, parent, false
        )
        return BaseViewHolder(binding)
    }
}