package com.halokonsultan.mobile.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.data.model.ParentCategory
import com.halokonsultan.mobile.databinding.ItemParentCategoryBinding

class ParentCategoryAdapter : BaseAdapter<ParentCategoryAdapter.ParentCategoryViewHolder, ParentCategory>() {

    inner class ParentCategoryViewHolder(val binding: ItemParentCategoryBinding): RecyclerView.ViewHolder(binding.root)

    private var onChildItemClickListener: ((Category) -> Unit)? = null

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
            onChildItemClickListener?.let { it -> it(category) }
        }
    }

    fun setOnChildItemClickListener(listener: (Category) -> Unit) {
        onChildItemClickListener = listener
    }
}