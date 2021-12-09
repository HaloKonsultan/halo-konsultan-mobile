package com.halokonsultan.mobile.ui.category

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.base.ActivityWithCustomToolbar
import com.halokonsultan.mobile.data.model.ParentCategory
import com.halokonsultan.mobile.data.model.dto.BasicResponse
import com.halokonsultan.mobile.databinding.ActivityCategoryBinding
import com.halokonsultan.mobile.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : ActivityWithCustomToolbar<ActivityCategoryBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCategoryBinding
        = ActivityCategoryBinding::inflate
    private lateinit var parentCategoryAdapter: ParentCategoryAdapter
    private val viewModel: CategoryViewModel by viewModels()

    override fun setup() {
        setupSupportActionBar()
        setTitle("Kategori Konsultan")
        setupRecyclerView()
        setupCategories()
    }

    private fun setupCategories() {
        viewModel.getAllCategories().observe(this, setupCategoryObserver())
    }

    private fun setupCategoryObserver() = setObserver<BasicResponse<List<ParentCategory>>>(
        onSuccess = { data ->
            parentCategoryAdapter.differ.submitList(data.data?.data)
        }
    )

    private fun setupRecyclerView() {
        parentCategoryAdapter = ParentCategoryAdapter()
        with(binding.rvParentCategory) {
            layoutManager = LinearLayoutManager(context)
            adapter = parentCategoryAdapter
        }

        parentCategoryAdapter.setOnChildItemClickListener { category ->
            val categoryName = "Konsultan ${category.name}"
            val intent = Intent(binding.root.context, SearchActivity::class.java)
            intent.putExtra(SearchActivity.EXTRA_CATEGORY_ID, category.id)
            intent.putExtra(SearchActivity.EXTRA_CATEGORY_NAME, categoryName)
            startActivity(intent)
        }
    }
}