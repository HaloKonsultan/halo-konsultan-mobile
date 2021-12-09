package com.halokonsultan.mobile.ui.profile

import android.Manifest
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.anggrayudi.storage.SimpleStorageHelper
import com.anggrayudi.storage.file.DocumentFileCompat
import com.anggrayudi.storage.file.copyFileTo
import com.anggrayudi.storage.file.mimeType
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.ActivityWithCustomToolbar
import com.halokonsultan.mobile.data.model.BankDocumentFile
import com.halokonsultan.mobile.databinding.ActivityBankDocumentBinding
import com.halokonsultan.mobile.utils.Utils.getCurrentDateTime
import com.halokonsultan.mobile.utils.Utils.openFile
import com.halokonsultan.mobile.utils.Utils.toString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class BankDocumentActivity : ActivityWithCustomToolbar<ActivityBankDocumentBinding>(),
    CopyFileCallback.OnCompleteListener {

    override val bindingInflater: (LayoutInflater) -> ActivityBankDocumentBinding
        = ActivityBankDocumentBinding::inflate
    private lateinit var fileAdapter: BankDocumentAdapter
    private val storageHelper = SimpleStorageHelper(this)
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun setup() {
        setupSupportActionBar()
        setTitle("Bank Dokumen")
        setupRecyclerView()
        setupStorageHelper()
        val takePhoto = setupTakePhotoContract()

        binding.cvAddDocument.setOnClickListener {
            askPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                onAccepted = {  storageHelper.openFilePicker(101) }
            )
        }

        binding.cvOpenCamera.setOnClickListener {
            askPermissions(
                Manifest.permission.CAMERA,
                onAccepted = { takePhoto.launch() }
            )
        }

        loadFilesFromInternalStorage()
    }

    override fun onComplete() {
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
                        CopyFileCallback(binding, uiScope, this@BankDocumentActivity)
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