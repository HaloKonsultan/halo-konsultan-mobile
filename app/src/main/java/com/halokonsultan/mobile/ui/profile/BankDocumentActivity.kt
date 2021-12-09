package com.halokonsultan.mobile.ui.profile

import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.listItems
import com.anggrayudi.storage.SimpleStorageHelper
import com.anggrayudi.storage.callback.FileCallback
import com.anggrayudi.storage.file.*
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.model.BankDocumentFile
import com.halokonsultan.mobile.databinding.ActivityBankDocumentBinding
import com.halokonsultan.mobile.utils.Utils
import com.halokonsultan.mobile.utils.Utils.getCurrentDateTime
import com.halokonsultan.mobile.utils.Utils.openFile
import com.halokonsultan.mobile.utils.Utils.toString
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*


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
        val takePhoto = setupTakePhotoContract()

        binding.cvAddDocument.setOnClickListener {
            storageHelper.openFilePicker(101)
        }

        binding.cvOpenCamera.setOnClickListener {
            takePhoto.launch()
        }

        loadFilesFromInternalStorage()
    }

    private fun setupTakePhotoContract() = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        if (it != null) {
            MaterialDialog(this)
                .cancelable(false)
                .title(R.string.masukkan_nama_file)
                .positiveButton(R.string.simpan)
                .input { _, text ->
                    savePhoto("${text}_${getCurrentDateTime().toString("yyyyMMdd_HHmmss")}", it)
                }
                .show()
        }
    }

    private fun savePhoto(name: String, bitmap: Bitmap) {
        val isSavedSuccessfully = savePhotoToInternalStorage(name, bitmap)
        if(isSavedSuccessfully) {
            loadFilesFromInternalStorage()
            Toast.makeText(this, "Foto berhasil disimpan", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Gagal menyimpan foto", Toast.LENGTH_SHORT).show()
        }
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
            files?.filter { it.canRead() && it.isFile &&
                !it.name.contains(Regex("generatefid?|PersistedInstallation?"))
            }?.map {
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
                            text = "Menyimpan file: 0%"
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
            tvStatus?.text = "Menyimpan file: ${report.progress.toInt()}%"
            progressBar?.isIndeterminate = false
            progressBar?.progress = report.progress.toInt()
        }

        override fun onFailed(errorCode: ErrorCode) {
            dialog?.dismiss()
            Toast.makeText(baseContext, "Gagal menyimpan file: $errorCode", Toast.LENGTH_SHORT).show()
        }

        override fun onCompleted(result: Any) {
            dialog?.dismiss()
            Toast.makeText(baseContext, "File berhasil disimpan", Toast.LENGTH_SHORT).show()
            loadFilesFromInternalStorage()
        }
    }

    private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean {
        return try {
            openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream ->
                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Tidak bisa menyimpan Bitmap.")
                }
            }
            true
        } catch(e: IOException) {
            e.printStackTrace()
            false
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
        }
    }
}