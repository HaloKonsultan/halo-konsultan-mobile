package com.halokonsultan.mobile.ui.uploaddocument

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BasicGridItem
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.gridItems
import com.afollestad.materialdialogs.files.FileFilter
import com.afollestad.materialdialogs.files.fileChooser
import com.anggrayudi.storage.SimpleStorageHelper
import com.anggrayudi.storage.file.DocumentFileCompat
import com.anggrayudi.storage.file.MimeType
import com.anggrayudi.storage.file.copyFileTo
import com.anggrayudi.storage.file.mimeType
import com.anggrayudi.storage.media.FileDescription
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.ActivityWithBackButton
import com.halokonsultan.mobile.data.model.ConsultationsDocument
import com.halokonsultan.mobile.data.model.DetailConsultation
import com.halokonsultan.mobile.databinding.ActivityUploadDocumentBinding
import com.halokonsultan.mobile.ui.consultation.DetailConsultationActivity
import com.halokonsultan.mobile.ui.profile.CopyFileCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class UploadDocumentActivity : ActivityWithBackButton<ActivityUploadDocumentBinding>(),
    CopyFileCallback.OnCompleteListener {

    companion object {
        const val EXTRA_DOC = "extra_doc"
        const val EXTRA_CONSULTATION_ID = "extra_consultaiton_id"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityUploadDocumentBinding
        = ActivityUploadDocumentBinding::inflate
    private lateinit var uploadAdapter: UploadAdapter
    private val viewModel: UploadViewModel by viewModels()
    private var docId: Int = 0
    private var consultationId: Int = 0
    private var selectedFile: DocumentFile? = null
    private val storageHelper = SimpleStorageHelper(this)
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun setup() {
        setupSupportActionBar()
        setupRecyclerView()
        populateDataFromBundle()
        setupButtonSubmit()

        storageHelper.onFileSelected = { _, file ->
            selectedFile = file[0]
            uploadWithTempFile()
        }
    }

    private fun uploadWithTempFile() {
        val targetFolder = DocumentFileCompat.fromFullPath(
            applicationContext,
            filesDir.absolutePath
        )
        if (targetFolder != null) {
            ioScope.launch {
                selectedFile?.copyFileTo(
                    applicationContext,
                    targetFolder,
                    FileDescription("HaloKonsultanTempFile", selectedFile?.mimeType ?: MimeType.UNKNOWN),
                    CopyFileCallback(binding, uiScope, this@UploadDocumentActivity)
                )
            }
        }
    }


    override fun onComplete() {
        viewModel.uploadDocument(contentResolver, selectedFile!!, consultationId, docId).observe(this, setupUploadObserver())
    }

    private fun setupButtonSubmit() {
        binding.btnSubmit.setOnClickListener {
            intent = Intent(this, DetailConsultationActivity::class.java)
            intent.putExtra(DetailConsultationActivity.EXTRA_ID, consultationId)
            startActivity(intent)
            finish()
        }
    }

    private fun setupUploadObserver() = setObserverWithBasicResponse<DetailConsultation>(
        onSuccess = { response ->
            binding.progressBar.gone()
            val files = response.data?.data?.consultation_document
            uploadAdapter.differ.submitList(files)
            if (files?.all { it.file != null } == true) {
                binding.btnSubmit.isEnabled = true
            }
            deleteSelectedFile()
        },
        onError = { response ->
            binding.progressBar.gone()
            showToast(response.message.toString())
        },
        onLoading = { binding.progressBar.visible() }
    )

    private fun deleteSelectedFile() {
        deleteFile("HaloKonsultanTempFile")
        selectedFile = null
    }

    private fun populateDataFromBundle() {
        val bundle = intent.extras
        if (bundle != null) {
            consultationId = intent.getIntExtra(EXTRA_CONSULTATION_ID, 0)
            val docs = intent.getParcelableArrayListExtra<ConsultationsDocument>(EXTRA_DOC)
            if (docs != null) {
                uploadAdapter.differ.submitList(docs)
                if (docs.all { it.file != null }) {
                    binding.btnSubmit.isEnabled = true
                }
            }
        }
    }

    private fun setupRecyclerView() {
        uploadAdapter = UploadAdapter()
        with(binding.rvDoc) {
            layoutManager = LinearLayoutManager(context)
            adapter = uploadAdapter
        }

        uploadAdapter.setOnItemClickListener { data ->
            docId = data.id

            askPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                onAccepted = {
                    handleUploadDocument()
                }
            )
        }
    }

    private fun handleUploadDocument() {
        val items = listOf(
            BasicGridItem(R.drawable.document, "Bank Dokumen"),
            BasicGridItem(R.drawable.upload, "File Explorer"),
        )

        MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT))
            .title(R.string.pilih_sumber_file)
            .gridItems(items, customGridWidth = R.integer.custom_grid_width, selection = { _, index, _ ->
                if (index == 0) openBankDocumentFilePicker()
                else storageHelper.openFilePicker()
            })
            .show()
    }

    private fun openBankDocumentFilePicker() {
        val initialFolder = File(filesDir.absolutePath)
        val filter: FileFilter = { it.canRead() && it.isFile &&
            !it.name.contains(Regex("generatefid?|PersistedInstallation?")) }
        MaterialDialog(this)
            .title(R.string.bank_dokumen)
            .fileChooser(initialDirectory = initialFolder, context = this, filter = filter, selection =
            { _, file ->
                viewModel.uploadDocument(contentResolver, DocumentFile.fromFile(file), consultationId, docId)
                .observe(this, setupUploadObserver())
            }).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        storageHelper.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        storageHelper.onRestoreInstanceState(savedInstanceState)
    }
}