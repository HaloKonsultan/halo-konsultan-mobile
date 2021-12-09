package com.halokonsultan.mobile.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.databinding.ItemCategoryBinding
import com.squareup.picasso.Picasso

class CategoryAdapter: BaseAdapter<CategoryAdapter.CategoryViewHolder, Category>() {

    inner class CategoryViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.binding.tvCategoryName.text = category.name

        Picasso.get().load(category.logo).into(holder.binding.imgCategoryIcon)

        holder.itemView.setOnClickListener {
            onRvItemClickListener?.let { it(category) }
        }
    }
}