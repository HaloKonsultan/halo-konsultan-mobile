package com.halokonsultan.mobile.ui.consultation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.FragmentConsultationListBinding
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

        viewModel.getConsultationListBasedOnStatus(viewModel.getUserID(), getStatus(type))
        viewModel.consultationList.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swiper.isRefreshing = false
                    consultationAdapter.differ.submitList(response.data)
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swiper.isRefreshing = false
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupSwiper() {
        binding.swiper.setOnRefreshListener {
            viewModel.getConsultationListBasedOnStatus(viewModel.getUserID(), getStatus(type))
        }
    }

    private fun setupRecyclerView() {
        consultationAdapter = ConsultationAdapter(type)
        with(binding.rvConsultation) {
            layoutManager = LinearLayoutManager(context)
            adapter = consultationAdapter
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