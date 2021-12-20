package com.halokonsultan.mobile.ui.consultation

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.BaseFragment
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.databinding.FragmentConsultationListBinding
import com.halokonsultan.mobile.utils.GlobalState
import com.halokonsultan.mobile.utils.TAB_TITLES
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsultationListFragment(private val type: Int) : BaseFragment<FragmentConsultationListBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentConsultationListBinding
        = FragmentConsultationListBinding::inflate
    private lateinit var consultationAdapter: ConsultationAdapter
    private val viewModel: ConsultationViewModel by activityViewModels()
    private var loading = false

    override fun setup() {
        setupRecyclerView()
        setupSwiper()
        setupData(1, false)
    }

    private fun setupSwiper() {
        binding.swiper.setOnRefreshListener {
            setupData(1, false)
        }
    }

    private fun setupData(page: Int, shouldAppend: Boolean) {
        viewModel.getConsultationByStatusAdvance(getStatus(type), page)
                .observe(viewLifecycleOwner, setupConsultationObserver(shouldAppend))
    }

    private fun setupConsultationObserver(shouldAppend: Boolean) = setObserver<List<Consultation>>(
        onSuccess = { response ->
            binding.progressBar.gone()
            binding.swiper.isRefreshing = false

            if (response.data != null && response.data.isNotEmpty()) {
                binding.layNoResult.gone()
                if (shouldAppend) {
                    val temp = consultationAdapter.differ.currentList.toMutableList()
                    temp.addAll(response.data)
                    consultationAdapter.differ.submitList(temp)
                    loading = false
                } else {
                    consultationAdapter.differ.submitList(response.data)
                }
                if (type == TAB_TITLES[2]) {
                    handleDoneConsultation()
                }
            } else {
                binding.layNoResult.visible()
                consultationAdapter.differ.submitList(null)
            }
        },
        onError = { response ->
            binding.progressBar.gone()
            binding.swiper.isRefreshing = false

            if (response.data!!.isEmpty()) {
                binding.layNoResult.visible()
            }

            Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
        },
        onLoading = { response ->
            consultationAdapter.differ.submitList(response.data)
            binding.progressBar.visible()
        }
    )

    private fun handleDoneConsultation() {
        val lists = consultationAdapter.differ.currentList
        lists.forEach { consultation ->
            viewModel.isReviewExist(consultation.id).observe(viewLifecycleOwner, { isExist ->
                if (!isExist) viewModel.upsertReview(consultation)
            })
        }
    }

    private fun setupRecyclerView() {
        consultationAdapter = ConsultationAdapter(type)
        with(binding.rvConsultation) {
            layoutManager = LinearLayoutManager(context)
            adapter = consultationAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                    val manager = this@with.layoutManager as LinearLayoutManager
                    val max = rv.adapter?.itemCount?.minus(1)
                    if (
                            GlobalState.nextPageConsultation != null
                            && (manager.findLastCompletelyVisibleItemPosition() == max)
                            && !loading
                    ) {
                        loading = true
                        setupData(GlobalState.nextPageConsultation!!, true)
                    }
                }
            })
        }

        consultationAdapter.setOnItemClickListener {
            val intent =  Intent(binding.root.context, DetailConsultationActivity::class.java)
            intent.putExtra(DetailConsultationActivity.EXTRA_ID, it.id)
            startActivity(intent)
        }
    }

    private fun getStatus(type: Int): String =
            when(type) {
                R.string.tab_text_1 -> "active"
                R.string.tab_text_2 -> "waiting"
                R.string.tab_text_3 -> "done"
                else -> "active"
    }
}