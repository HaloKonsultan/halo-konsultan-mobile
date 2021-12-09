package com.halokonsultan.mobile.base

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH: RecyclerView.ViewHolder, Item: BaseModel>: RecyclerView.Adapter<VH>() {
    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldModel: Item, newModel: Item) =  oldModel == newModel
    }

    val differ = AsyncListDiffer(this, differCallback)
    protected var onRvItemClickListener: ((Item) -> Unit)? = null

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onRvItemClickListener = listener
    }
}