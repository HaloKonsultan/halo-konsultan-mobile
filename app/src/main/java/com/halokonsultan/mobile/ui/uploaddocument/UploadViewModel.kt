package com.halokonsultan.mobile.ui.uploaddocument

import android.content.ContentResolver
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.DetailConsultation
import com.halokonsultan.mobile.utils.InputStreamRequestBody
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
        private val repository: BaseRepository
) : ViewModel() {

    private val _upload: MutableLiveData<Resource<DetailConsultation>> = MutableLiveData()
    val upload: LiveData<Resource<DetailConsultation>>
    get() = _upload

    fun uploadDocument(contentResolver: ContentResolver, file: DocumentFile, consultationId: Int, docId: Int) = viewModelScope.launch {
        _upload.postValue(Resource.Loading())
        try {
//            val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val requestFile: RequestBody = InputStreamRequestBody(null, contentResolver, file.uri)
            val body = requestFile.let { MultipartBody.Part.createFormData("file", file.name, it) }
            val response = repository.uploadDocument(body, consultationId, docId).body()
            if (response?.data != null) {
                _upload.postValue(Resource.Success(response.data))
            }
        } catch (e: Exception) {
            _upload.postValue(Resource.Error(e.message ?: "unknown error"))
        }
    }

}