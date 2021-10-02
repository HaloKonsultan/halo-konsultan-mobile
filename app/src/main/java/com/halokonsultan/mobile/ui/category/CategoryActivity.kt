package com.halokonsultan.mobile.ui.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.ActivityCategoryBinding
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
    }
}