package com.halokonsultan.mobile.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.databinding.FragmentHomeBinding
import com.halokonsultan.mobile.ui.category.CategoryActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantAdapter
import com.halokonsultan.mobile.ui.search.SearchActivity
import com.halokonsultan.mobile.utils.DummyData
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var consultantAdapter: ConsultantAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.getRandomCategoriesAdvance().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.categoryProgressBar.isVisible = false
                    categoryAdapter.differ.submitList(viewModel.categoriesWithOther(response.data))
                }

                is Resource.Error -> {
                    binding.categoryProgressBar.isVisible = false
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    binding.categoryProgressBar.isVisible = true
                    categoryAdapter.differ.submitList(viewModel.categoriesWithOther(response.data))
                }
            }
        })

        viewModel.getNearestConsultantsAdvance("surabaya").observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.consultantProgressBar.isVisible = false
                    consultantAdapter.differ.submitList(response.data)
                }

                is Resource.Error -> {
                    binding.consultantProgressBar.isVisible = false
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    binding.consultantProgressBar.isVisible = true
                    consultantAdapter.differ.submitList(response.data)
                }
            }
        })

        binding.btnSearch.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter()
        with(binding.rvCategory) {
            layoutManager = GridLayoutManager(context, 3)
            adapter = categoryAdapter
        }

        categoryAdapter.setOnItemClickListener { category ->
            if (category.id == 999) {
                val intent = Intent(binding.root.context, CategoryActivity::class.java)
                startActivity(intent)
            } else {
                val categoryName = "Konsultan ${category.name}"
                val intent = Intent(binding.root.context, SearchActivity::class.java)
                intent.putExtra(SearchActivity.EXTRA_CATEGORY_ID, category.id)
                intent.putExtra(SearchActivity.EXTRA_CATEGORY_NAME, categoryName)
                startActivity(intent)
            }

        }

        consultantAdapter = ConsultantAdapter()
        with(binding.rvNearestConsultant) {
            layoutManager = LinearLayoutManager(context)
            adapter = consultantAdapter
        }

        consultantAdapter.setOnItemClickListener {
            val intent = Intent(binding.root.context, ConsultantActivity::class.java)
            intent.putExtra(ConsultantActivity.EXTRA_ID, it.id)
            startActivity(intent)
        }
    }
}