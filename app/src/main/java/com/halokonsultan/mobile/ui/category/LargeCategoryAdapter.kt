package com.halokonsultan.mobile.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.databinding.ItemCategoryBinding
import com.halokonsultan.mobile.databinding.ItemCategoryLargeBinding

class LargeCategoryAdapter: RecyclerView.Adapter<LargeCategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemCategoryLargeBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldCategory: Category, newCategory: Category) =  oldCategory == newCategory
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = ItemCategoryLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = differ.currentList[position]
        val categoryName = "Konsultan ${category.name}"
        holder.binding.tvCategoryName.text = categoryName
        Glide.with(holder.itemView.context)
            .load(R.drawable.ic_other_category)
            .into(holder.binding.imgCategoryIcon)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(category) }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(listener: (Category) -> Unit) {
        onItemClickListener = listener
    }
}