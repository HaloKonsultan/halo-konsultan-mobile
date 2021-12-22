package com.halokonsultan.mobile.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.databinding.ItemCategoryLargeBinding
import com.squareup.picasso.Picasso

class LargeCategoryAdapter: BaseAdapter<LargeCategoryAdapter.CategoryViewHolder, Category>() {

    inner class CategoryViewHolder(val binding: ItemCategoryLargeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = ItemCategoryLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = differ.currentList[position]
        val categoryName = category.name
        holder.binding.tvCategoryName.text = categoryName

        Picasso.get().load(category.logo).into(holder.binding.imgCategoryIcon)

        holder.itemView.setOnClickListener {
            onRvItemClickListener?.let { it(category) }
        }
    }
}