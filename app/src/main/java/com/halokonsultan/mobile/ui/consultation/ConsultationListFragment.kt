package com.halokonsultan.mobile.ui.consultation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.FragmentConsultationListBinding
import com.halokonsultan.mobile.utils.GlobalState
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsultationListFragment(private val type: Int) : Fragment() {

    private lateinit var binding: FragmentConsultationListBinding
    private lateinit var consultationAdapter: ConsultationAdapter
    private val viewModel: ConsultationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConsultationListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwiper()
        setupData()
    }

    private fun setupSwiper() {
        binding.swiper.setOnRefreshListener {
            GlobalState.nextPageConsultation = 1
            setupData()
        }
    }

    private fun setupData() {
        viewModel.getConsultationByStatusAdvance(getStatus(type), GlobalState.nextPageConsultation ?: 1)
                .observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    binding.swiper.isRefreshing = false
                    val page = GlobalState.nextPageConsultation
                    if ((page == null || page > 2) && response.data != null) {
                        val temp = consultationAdapter.differ.currentList.toMutableList()
                        temp.addAll(response.data)
                        consultationAdapter.differ.submitList(temp)
                    } else {
                        consultationAdapter.differ.submitList(response.data)
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    binding.swiper.isRefreshing = false
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    consultationAdapter.differ.submitList(response.data)
                    binding.progressBar.isVisible = true
                }
            }
        })
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
                    ) {
                        setupData()
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