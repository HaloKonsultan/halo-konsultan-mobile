package com.halokonsultan.mobile.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.databinding.FragmentHomeBinding
import com.halokonsultan.mobile.ui.category.CategoryActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantAdapter
import com.halokonsultan.mobile.ui.search.SearchActivity
import com.halokonsultan.mobile.utils.GlobalState
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var consultantAdapter: ConsultantAdapter
    private val viewModel: HomeViewModel by viewModels()
    private var loading = false

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
        getNearestConsultantData(1, false)
        getRandomCategories()

        binding.btnSearch.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getRandomCategories() {
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
    }

    private fun getNearestConsultantData(page: Int, shouldAppend: Boolean) {
        viewModel.getNearestConsultantsAdvance(viewModel.getUserCity() ?: "jakarta", page)
                .observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.consultantProgressBar.isVisible = false
                    if (shouldAppend) {
                        val temp = consultantAdapter.differ.currentList.toMutableList()
                        response.data?.let { temp.addAll(it) }
                        consultantAdapter.differ.submitList(temp)
                        loading = false
                    } else {
                        consultantAdapter.differ.submitList(response.data)
                    }
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

            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                    val manager = this@with.layoutManager as LinearLayoutManager
                    val max = rv.adapter?.itemCount?.minus(1)
                    if (
                        GlobalState.nextPageConsultant != null
                        && manager.findLastCompletelyVisibleItemPosition() == max
                        && !loading
                    ) {
                        loading = true
                        getNearestConsultantData(GlobalState.nextPageConsultant!!, true)
                    }
                }
            })
        }

        consultantAdapter.setOnItemClickListener {
            val intent = Intent(binding.root.context, ConsultantActivity::class.java)
            intent.putExtra(ConsultantActivity.EXTRA_ID, it.id)
            startActivity(intent)
        }
    }
}