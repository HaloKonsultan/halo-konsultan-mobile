package com.halokonsultan.mobile.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseFragment
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.databinding.FragmentHomeBinding
import com.halokonsultan.mobile.ui.category.CategoryActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantAdapter
import com.halokonsultan.mobile.ui.search.SearchActivity
import com.halokonsultan.mobile.utils.GlobalState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        = FragmentHomeBinding::inflate
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var consultantAdapter: ConsultantAdapter
    private val viewModel: HomeViewModel by viewModels()
    private var loading = false

    override fun setup() {
        setupRecyclerView()
        getNearestConsultantData(1, false)
        getRandomCategories()

        binding.btnSearch.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupCategoriesObserver() = setObserver<List<Category>>(
        onSuccess = { response ->
            binding.categoryProgressBar.gone()
            categoryAdapter.differ.submitList(viewModel.categoriesWithOther(response.data))
        },
        onError = { response ->
            binding.categoryProgressBar.gone()
            showToast(response.message.toString())
        },
        onLoading = { response ->
            binding.categoryProgressBar.visible()
            categoryAdapter.differ.submitList(viewModel.categoriesWithOther(response.data))
        }
    )

    private fun setupConsultantObserver(shouldAppend: Boolean) = setObserver<List<Consultant>>(
        onSuccess = { response ->
            binding.consultantProgressBar.gone()
            if (response.data!!.isNotEmpty()) {
                binding.layNoResult.gone()
                if (shouldAppend) {
                    val temp = consultantAdapter.differ.currentList.toMutableList()
                    temp.addAll(response.data)
                    consultantAdapter.differ.submitList(temp)
                    loading = false
                } else {
                    consultantAdapter.differ.submitList(response.data)
                }
            } else {
                binding.layNoResult.visible()
            }
        },
        onError = { response ->
            binding.consultantProgressBar.gone()
            showToast(response.message.toString())
        },
        onLoading = { response ->
            binding.consultantProgressBar.visible()
            consultantAdapter.differ.submitList(response.data)
        }
    )

    private fun getRandomCategories() {
        viewModel.getRandomCategoriesAdvance().observe(viewLifecycleOwner, setupCategoriesObserver())
    }

    private fun getNearestConsultantData(page: Int, shouldAppend: Boolean) {
        viewModel.getNearestConsultantsAdvance(viewModel.getUserCity() ?: "jakarta", page)
                .observe(viewLifecycleOwner, setupConsultantObserver(shouldAppend))
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