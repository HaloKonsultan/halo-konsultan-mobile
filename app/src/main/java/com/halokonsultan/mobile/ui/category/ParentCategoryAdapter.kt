package com.halokonsultan.mobile.ui.category

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.data.model.ParentCategory
import com.halokonsultan.mobile.databinding.ItemParentCategoryBinding

class ParentCategoryAdapter : RecyclerView.Adapter<ParentCategoryAdapter.ParentCategoryViewHolder>() {

    inner class ParentCategoryViewHolder(val binding: ItemParentCategoryBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ParentCategory>() {
        override fun areItemsTheSame(oldItem: ParentCategory, newItem: ParentCategory) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldParentCategory: ParentCategory, newParentCategory: ParentCategory)
        =  oldParentCategory == newParentCategory
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentCategoryViewHolder {
        val itemBinding = ItemParentCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentCategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ParentCategoryViewHolder, position: Int) {
        val parentCategory = differ.currentList[position]
        val categoryName = "Konsultan ${parentCategory.name}"
        val categoryAdapter = LargeCategoryAdapter()

        with(holder.binding) {
            tvParentCategoryName.text = categoryName
            rvCategory.layoutManager = GridLayoutManager(holder.itemView.context, 2)
            rvCategory.adapter = categoryAdapter
        }

        categoryAdapter.differ.submitList(parentCategory.child)
        categoryAdapter.setOnItemClickListener { category ->
            onItemClickListener?.let { it -> it(category) }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(listener: (Category) -> Unit) {
        onItemClickListener = listener
    }
}