package com.halokonsultan.mobile.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.ActivityCategoryBinding
import com.halokonsultan.mobile.ui.search.SearchActivity
import com.halokonsultan.mobile.utils.DummyData
import com.halokonsultan.mobile.utils.Utils

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var parentCategoryAdapter: ParentCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.title_text_view)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        Utils.setTitleTextView(this, "Kategori Konsultan")

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}