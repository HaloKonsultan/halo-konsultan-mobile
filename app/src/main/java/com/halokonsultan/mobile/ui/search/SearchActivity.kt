package com.halokonsultan.mobile.ui.search
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.databinding.ActivitySearchBinding
import com.halokonsultan.mobile.ui.home.ConsultantAdapter
import com.halokonsultan.mobile.utils.DummyData


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var consultantAdapter: ConsultantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        consultantAdapter.differ.submitList(DummyData.getConsultantList())

    }

    private fun setupRecyclerView() {
        consultantAdapter = ConsultantAdapter()
        with(binding.rvSearchConsultan) {
            layoutManager = LinearLayoutManager(context)
            adapter = consultantAdapter
        }

        consultantAdapter.setOnItemClickListener {
            Log.d("coba", "setupRecyclerView: consultant clicked $it")
        }
    }
}