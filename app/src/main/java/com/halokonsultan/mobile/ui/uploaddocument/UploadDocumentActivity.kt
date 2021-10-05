package com.halokonsultan.mobile.ui.uploaddocument

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.databinding.ActivityUploadDocumentBinding
import com.halokonsultan.mobile.utils.DummyData

class UploadDocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadDocumentBinding
    private lateinit var uploadAdapter: UploadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        uploadAdapter.differ.submitList(DummyData.getUploadDocumentList())
        Log.d("coba", "Ini Log RecyclerView: ${uploadAdapter.itemCount}")
    }

    private fun setupRecyclerView() {
        uploadAdapter = UploadAdapter()
        with(binding.rvDoc) {
            layoutManager = LinearLayoutManager(context)
            adapter = uploadAdapter
        }
    }
}