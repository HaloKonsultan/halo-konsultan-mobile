package com.halokonsultan.mobile.ui.profile

import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.list.listItems
import com.anggrayudi.storage.callback.FileCallback
import com.anggrayudi.storage.file.FileSize
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.ActivityBankDocumentBinding
import kotlinx.coroutines.CoroutineScope

class CopyFileCallback(val binding: ActivityBankDocumentBinding, uiScope: CoroutineScope, defaultListener: OnCompleteListener) : FileCallback(uiScope) {
    var dialog: MaterialDialog? = null
    var tvStatus: TextView? = null
    var progressBar: ProgressBar? = null
    private val listeners: MutableList<OnCompleteListener> = mutableListOf(defaultListener)

    override fun onConflict(destinationFile: DocumentFile, action: FileConflictAction) {
        handleFileConflict(action)
    }

    override fun onStart(file: Any, workerThread: Thread): Long {
        if ((file as DocumentFile).length() > 10 * FileSize.MB) {
            dialog = MaterialDialog(binding.root.context)
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
        Toast.makeText(binding.root.context, "Gagal menyimpan file: $errorCode", Toast.LENGTH_SHORT).show()
    }

    override fun onCompleted(result: Any) {
        dialog?.dismiss()
        Toast.makeText(binding.root.context, "File berhasil disimpan", Toast.LENGTH_SHORT).show()
        listeners.forEach {
            it.onComplete()
        }
    }

    interface OnCompleteListener {
        fun onComplete()
    }
}