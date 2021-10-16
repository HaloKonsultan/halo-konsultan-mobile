package com.halokonsultan.mobile.ui.uploaddocument

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.data.model.ConsultationsDocument
import com.halokonsultan.mobile.databinding.ActivityUploadDocumentBinding
import dagger.hilt.android.AndroidEntryPoint
import me.rosuh.filepicker.config.FilePickerManager
import java.io.File


@AndroidEntryPoint
class UploadDocumentActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DOC = "extra_doc"
        const val EXTRA_CONSULTATION_ID = "extra_consultaiton_id"
    }

    private lateinit var binding: ActivityUploadDocumentBinding
    private lateinit var uploadAdapter: UploadAdapter
    private val viewModel: UploadViewModel by viewModels()
    private var docId: Int = 0
    private var consultationId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupRecyclerView()
        val bundle = intent.extras
        if (bundle != null) {
            consultationId = intent.getIntExtra(EXTRA_CONSULTATION_ID, 0)
            val docs = intent.getParcelableArrayListExtra<ConsultationsDocument>(EXTRA_DOC)
            if (docs != null) {
                uploadAdapter.differ.submitList(docs)
            }
        }
    }

    private fun setupRecyclerView() {
        uploadAdapter = UploadAdapter()
        with(binding.rvDoc) {
            layoutManager = LinearLayoutManager(context)
            adapter = uploadAdapter
        }

        uploadAdapter.setOnUploadClickListener { data ->
            docId = data.id
            FilePickerManager
                .from(this)
                .maxSelectable(1)
                .showCheckBox(false)
                .forResult(FilePickerManager.REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FilePickerManager.REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val list = FilePickerManager.obtainData()
                    list.forEach { filePath ->
                        val mFile = File(filePath)
                        Log.d("coba", "onActivityResult: ${mFile.name}")
                    }
                    val mFile = File(list[0])
                    viewModel.uploadDocument(mFile, consultationId, docId )
                } else {
                    Toast.makeText(
                        this@UploadDocumentActivity,
                        "You didn't choose anything~", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}