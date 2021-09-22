package com.halokonsultan.mobile.ui.consultation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.databinding.FragmentConsultationListBinding
import com.halokonsultan.mobile.utils.DummyData

class ConsultationListFragment(private val type: Int) : Fragment() {

    private lateinit var binding: FragmentConsultationListBinding
    private lateinit var consultationAdapter: ConsultationAdapter

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

        consultationAdapter.differ.submitList(DummyData.getConsultationList())
    }

    private fun setupRecyclerView() {
        consultationAdapter = ConsultationAdapter()
        with(binding.rvConsultation) {
            layoutManager = LinearLayoutManager(context)
            adapter = consultationAdapter
        }

        consultationAdapter.setOnItemClickListener {
            Log.d("coba", "setupRecyclerView: category clicked $it")
        }
    }
}