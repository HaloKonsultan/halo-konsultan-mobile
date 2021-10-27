package com.halokonsultan.mobile.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.databinding.ActivityCategoryBinding
import com.halokonsultan.mobile.ui.search.SearchActivity
import com.halokonsultan.mobile.utils.DummyData

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var parentCategoryAdapter: ParentCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        parentCategoryAdapter.differ.submitList(DummyData.getParentCategoryList())
    }

    private fun setupRecyclerView() {
        parentCategoryAdapter = ParentCategoryAdapter()
        with(binding.rvParentCategory) {
            layoutManager = LinearLayoutManager(context)
            adapter = parentCategoryAdapter
        }

        parentCategoryAdapter.setOnItemClickListener { category ->
            val categoryName = "Konsultan ${category.name}"
            val intent = Intent(binding.root.context, SearchActivity::class.java)
            intent.putExtra(SearchActivity.EXTRA_CATEGORY_ID, category.id)
            intent.putExtra(SearchActivity.EXTRA_CATEGORY_NAME, categoryName)
            startActivity(intent)
        }
    }
}