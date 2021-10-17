package com.halokonsultan.mobile.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.ActivitySearchBinding
import com.halokonsultan.mobile.ui.category.CategoryActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantAdapter
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.SEARCH_USER_TIME_DELAY
import com.halokonsultan.mobile.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY_ID = "extra_category_id"
        const val EXTRA_CATEGORY_NAME = "extra_category_name"
    }

    private lateinit var binding: ActivitySearchBinding
    private lateinit var consultantAdapter: ConsultantAdapter
    private val viewModel: SearchViewModel by viewModels()
    private var showCategory = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.title_text_view)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        Utils.setTitleTextView(this, "Cari Konsultan")

        val bundle = intent.extras
        if (bundle != null) {
            val categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, 0)
            val categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME)
            val searchResultText = "Hasil Pencarian \"${categoryName}\""

            showCategory = true
            binding.tvResult.text = searchResultText
            binding.tvResult.visibility = View.VISIBLE
            binding.tfSearch.visibility = View.GONE
            viewModel.searchConsultantByCategory(categoryId)
        }

        if (!showCategory) {
            var job: Job? = null
            binding.etSearch.addTextChangedListener { editable ->
                job?.cancel()
                if (editable.toString().isNotEmpty()) {
                    job = MainScope().launch {
                        delay(SEARCH_USER_TIME_DELAY)
                        editable?.let {
                            val searchResultText = "Hasil Pencarian \"${editable}\""
                            viewModel.searchConsultantByName(editable.toString())
                            binding.tvResult.text = searchResultText
                            binding.tvResult.visibility = View.VISIBLE
                        }
                    }
                } else {
                    consultantAdapter.differ.submitList(null)
                    binding.tvResult.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.consultants.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    consultantAdapter.differ.submitList(response.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })

//        consultantAdapter.differ.submitList(DummyData.getConsultantList())
        binding.tvShowCategory.setOnClickListener {
            val intent = Intent(this@SearchActivity, CategoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        consultantAdapter = ConsultantAdapter()
        with(binding.rvSearchConsultan) {
            layoutManager = LinearLayoutManager(context)
            adapter = consultantAdapter
        }

        consultantAdapter.setOnItemClickListener {
            val intent = Intent(this@SearchActivity, ConsultantActivity::class.java)
            intent.putExtra(ConsultantActivity.EXTRA_ID, it.id)
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