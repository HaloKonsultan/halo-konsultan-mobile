package com.halokonsultan.mobile.ui.uploaddocument

import android.content.ContentResolver
import androidx.documentfile.provider.DocumentFile
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.utils.InputStreamRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
        private val repository: BaseRepository
) : BaseViewModel() {

    fun uploadDocument(contentResolver: ContentResolver, file: DocumentFile, consultationId: Int, docId: Int) = callApiReturnLiveData(
        apiCall = {
            val requestFile: RequestBody = InputStreamRequestBody(null, contentResolver, file.uri)
            val body = requestFile.let { MultipartBody.Part.createFormData("file", file.name, it) }
            repository.uploadDocument(body, consultationId, docId)
        }
    )

}