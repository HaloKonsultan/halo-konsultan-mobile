package com.halokonsultan.mobile.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.ActivityWithCustomToolbar
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.databinding.ActivitySearchBinding
import com.halokonsultan.mobile.ui.category.CategoryActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantAdapter
import com.halokonsultan.mobile.utils.GlobalState
import com.halokonsultan.mobile.utils.SEARCH_USER_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : ActivityWithCustomToolbar<ActivitySearchBinding>() {

    companion object {
        const val EXTRA_CATEGORY_ID = "extra_category_id"
        const val EXTRA_CATEGORY_NAME = "extra_category_name"
    }

    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding
        = ActivitySearchBinding::inflate
    private lateinit var consultantAdapter: ConsultantAdapter
    private val viewModel: SearchViewModel by viewModels()
    private var showCategory = false
    private var categoryId = 0
    private var loading = false

    override fun setup() {
        setupSupportActionBar()
        setTitle("Cari Konsultan")
        setupRecyclerView()

        populateDataFromBundle()

        if (!showCategory) {
           handleSearchByName()
        }

        setupClickListener()
    }

    private fun populateDataFromBundle() {
        val bundle = intent.extras
        if (bundle != null) {
            categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, 0)
            val categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME)
            val searchResultText = "Hasil Pencarian \"${categoryName}\""

            showCategory = true
            binding.tvResult.text = searchResultText
            binding.tvResult.visibility = View.VISIBLE
            binding.tfSearch.visibility = View.GONE
            viewModel.searchConsultantByCategoryAdvance(categoryId, 1)
                .observe(this@SearchActivity, setupObserver(false))

        }
    }

    private fun handleSearchByName() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            if (editable.toString().isNotEmpty()) {
                job = MainScope().launch {
                    delay(SEARCH_USER_TIME_DELAY)
                    editable?.let {
                        val searchResultText = "Hasil Pencarian \"${editable}\""
                        viewModel.searchConsultantByName(editable.toString(), 1)
                            .observe(this@SearchActivity, setObserverWithPaginate(false))
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

    private fun setupClickListener() {
        binding.tvShowCategory.setOnClickListener {
            val intent = Intent(this@SearchActivity, CategoryActivity::class.java)
            startActivity(intent)
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.searchConsultantByName(binding.etSearch.toString(), 1)
        }
    }

    private fun setupObserver(shouldAppend: Boolean) = setObserver<List<Consultant>>(
        onSuccess = { response ->
            binding.progressBar.gone()
            binding.layNoInet.gone()

            if ((response.data as List<Consultant>).isNotEmpty()) {
                binding.lottieNoResult.gone()
                binding.tvNoResult.gone()
                if (shouldAppend) {
                    val temp = consultantAdapter.differ.currentList.toMutableList()
                    temp.addAll(response.data)
                    consultantAdapter.differ.submitList(temp)
                } else {
                    consultantAdapter.differ.submitList(response.data)
                }
            } else {
                binding.lottieNoResult.visible()
                binding.tvNoResult.visible()
                consultantAdapter.differ.submitList(null)
            }
            loading = false
        },
        onError = { response ->
            binding.progressBar.gone()
            binding.layNoInet.visible()
            showToast(response.message.toString())
        },
        onLoading = { response ->
            binding.progressBar.visible()
            consultantAdapter.differ.submitList(response.data)
        }
    )

    private fun setObserverWithPaginate(shouldAppend: Boolean) = setObserverWithPaginationResponse<Consultant>(
        onSuccess = { response ->
            val data = response.data?.data?.data

            binding.progressBar.gone()
            binding.layNoInet.gone()

            if ((data as List<Consultant>).isNotEmpty()) {
                binding.layNoResult.gone()
                if (shouldAppend) {
                    val temp = consultantAdapter.differ.currentList.toMutableList()
                    temp.addAll(data)
                    consultantAdapter.differ.submitList(temp)
                } else {
                    consultantAdapter.differ.submitList(data)
                }
            } else {
                binding.layNoResult.visible()
                consultantAdapter.differ.submitList(null)
            }
            loading = false
        },
        onError = { response ->
            binding.progressBar.gone()
            binding.layNoInet.visible()
            binding.layNoResult.gone()
            showToast(response.message.toString())
        },
        onLoading = { binding.progressBar.visible() }
    )

    private fun setupRecyclerView() {
        consultantAdapter = ConsultantAdapter()
        with(binding.rvSearchConsultan) {
            layoutManager = LinearLayoutManager(context)
            adapter = consultantAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                    val manager = this@with.layoutManager as LinearLayoutManager
                    val max = rv.adapter?.itemCount?.minus(1)
                    if (
                            GlobalState.nextPageConsultant != null
                            && (manager.findLastCompletelyVisibleItemPosition() == max)
                            && !loading
                    ) {
                        loading = true
                        if (showCategory) {
                            viewModel.searchConsultantByCategoryAdvance(categoryId, GlobalState.nextPageConsultant!!)
                                .observe(this@SearchActivity, setupObserver(true))
                        } else {
                            viewModel.searchConsultantByName(binding.etSearch.text.toString(), GlobalState.nextPageConsultant!!)
                                .observe(this@SearchActivity, setObserverWithPaginate(true))
                        }
                    }
                }
            })
        }

        consultantAdapter.setOnItemClickListener {
            val intent = Intent(this@SearchActivity, ConsultantActivity::class.java)
            intent.putExtra(ConsultantActivity.EXTRA_ID, it.id)
            startActivity(intent)
        }
    }
}