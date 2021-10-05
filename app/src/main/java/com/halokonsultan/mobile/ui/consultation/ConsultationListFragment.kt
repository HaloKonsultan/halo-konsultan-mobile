package com.halokonsultan.mobile.ui.consultation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.databinding.FragmentConsultationListBinding
import com.halokonsultan.mobile.utils.DummyData
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.TAB_TITLES
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

        viewModel.getConsultationListBasedOnStatus(1, resources.getString(type), 10, 1)
        Log.d("coba", "onViewCreated: ${resources.getString(type)}")
        viewModel.consultationList.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    consultationAdapter.differ.submitList(response.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })

        // dummy
        when (type) {
            TAB_TITLES[0] -> {
                consultationAdapter.differ.submitList(DummyData.getConsultationList())
            }
            TAB_TITLES[1] -> {
                consultationAdapter.differ.submitList(DummyData.getWaitingConsultationList())
            }
            TAB_TITLES[2] -> {
                consultationAdapter.differ.submitList(DummyData.getDoneConsultationList())
            }
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
}