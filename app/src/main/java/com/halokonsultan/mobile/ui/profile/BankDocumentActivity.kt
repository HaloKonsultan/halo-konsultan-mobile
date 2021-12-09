package com.halokonsultan.mobile.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.list.listItems
import com.anggrayudi.storage.SimpleStorageHelper
import com.anggrayudi.storage.callback.FileCallback
import com.anggrayudi.storage.file.*
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.model.BankDocumentFile
import com.halokonsultan.mobile.databinding.ActivityBankDocumentBinding
import com.halokonsultan.mobile.utils.Utils
import com.halokonsultan.mobile.utils.Utils.openFile
import kotlinx.coroutines.*


class BankDocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankDocumentBinding
    private lateinit var fileAdapter: BankDocumentAdapter
    private val storageHelper = SimpleStorageHelper(this)
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.title_text_view)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        Utils.setTitleTextView(this, "Bank Dokumen")

        setupRecyclerView()
        setupStorageHelper()

        binding.cvAddDocument.setOnClickListener {
            storageHelper.openFilePicker(101)
        }

        loadFilesFromInternalStorage()
    }

    private fun loadFilesFromInternalStorage() {
        uiScope.launch {
            val files = loadFiles()
            fileAdapter.differ.submitList(files)
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

    private suspend fun loadFiles(): List<BankDocumentFile> {
        return withContext(Dispatchers.IO) {
            val files = filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile }?.map {
                val name = it.name
                val type = it.mimeType ?: "other file"
                BankDocumentFile(name, type, DocumentFile.fromFile(it))
            } ?: listOf()
        }
    }

    private val copyFileCallback = object : FileCallback(uiScope) {

        var dialog: MaterialDialog? = null
        var tvStatus: TextView? = null
        var progressBar: ProgressBar? = null

        override fun onConflict(destinationFile: DocumentFile, action: FileConflictAction) {
            handleFileConflict(action)
        }

        override fun onStart(file: Any, workerThread: Thread): Long {
            if ((file as DocumentFile).length() > 10 * FileSize.MB) {
                dialog = MaterialDialog(this@BankDocumentActivity)
                    .cancelable(false)
                    .positiveButton(android.R.string.cancel) { workerThread.interrupt() }
                    .customView(R.layout.dialog_copy_progress).apply {
                        tvStatus = getCustomView().findViewById<TextView>(R.id.tvProgressStatus).apply {
                            text = "Copying file: 0%"
                        }

                        progressBar = getCustomView().findViewById<ProgressBar>(R.id.progressCopy).apply {
                            isIndeterminate = true
                        }
                        show()
                    }
            }
            return 500
        }

        private fun handleFileConflict(action: FileConflictAction) {
            MaterialDialog(binding.root.context)
                .cancelable(false)
                .title(text = "Terjadi Konflik!")
                .message(text = "File sudah ada, Apa yang ingin Anda lakukan?")
                .listItems(items = listOf("Ganti", "Buat Baru", "Lewati file duplikat")) { _, index, _ ->
                    val resolution = ConflictResolution.values()[index]
                    action.confirmResolution(resolution)
                    if (resolution == ConflictResolution.SKIP) {
                        Toast.makeText(
                            binding.root.context,
                            "Melewatkan file duplikat",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .show()
        }

        override fun onReport(report: Report) {
            tvStatus?.text = "Copying file: ${report.progress.toInt()}%"
            progressBar?.isIndeterminate = false
            progressBar?.progress = report.progress.toInt()
        }

        override fun onFailed(errorCode: ErrorCode) {
            dialog?.dismiss()
            Toast.makeText(baseContext, "Failed copying file: $errorCode", Toast.LENGTH_SHORT).show()
        }

        override fun onCompleted(result: Any) {
            dialog?.dismiss()
            Toast.makeText(baseContext, "File copied successfully", Toast.LENGTH_SHORT).show()
            loadFilesFromInternalStorage()
        }
    }

    private fun setupStorageHelper() {
        storageHelper.onFileSelected = { _, file ->
            val selectedFile = file[0]
            val targetFolder = DocumentFileCompat.fromFullPath(
                applicationContext,
                filesDir.absolutePath
            )
            if (targetFolder != null) {
                ioScope.launch {
                    selectedFile.copyFileTo(
                        applicationContext,
                        targetFolder,
                        null,
                        copyFileCallback
                    )
                }
            }
        }
    }

    private fun setupRecyclerView() {
        fileAdapter = BankDocumentAdapter()
        with(binding.rvBankDocument) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = fileAdapter
        }

        fileAdapter.setOnItemClickListener { file ->
//            Utils.openFile(applicationContext, file.file)
            openFile(file.name)
        }

        fileAdapter.setOnLongItemClickListener { file ->
            MaterialDialog(binding.root.context)
                .cancelable(true)
                .title(text = "Hapus file")
                .message(text = "Anda yakin ingin menghapus file ini?")
                .positiveButton(text = "Hapus") {
                    deleteFile(file.name)
                    loadFilesFromInternalStorage()
                }
                .negativeButton(text = "Batalkan") {
                    it.dismiss()
                }
                .show()
            Log.d("coba", "setupRecyclerView: $file")
        }
    }
}