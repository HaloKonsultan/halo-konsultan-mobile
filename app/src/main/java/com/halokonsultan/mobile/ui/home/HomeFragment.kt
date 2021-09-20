package com.halokonsultan.mobile.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.databinding.FragmentHomeBinding
import com.halokonsultan.mobile.utils.DummyData

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var consultantAdapter: ConsultantAdapter

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

        consultantAdapter.differ.submitList(DummyData.getConsultantList())
        categoryAdapter.differ.submitList(DummyData.getCategoryList())
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter()
        with(binding.rvCategory) {
            layoutManager = GridLayoutManager(context, 3)
            adapter = categoryAdapter
        }

        categoryAdapter.setOnItemClickListener {
            Log.d("coba", "setupRecyclerView: category clicked $it")
        }

        consultantAdapter = ConsultantAdapter()
        with(binding.rvNearestConsultant) {
            layoutManager = LinearLayoutManager(context)
            adapter = consultantAdapter
        }

        consultantAdapter.setOnItemClickListener {
            Log.d("coba", "setupRecyclerView: consultant clicked $it")
        }
    }
}