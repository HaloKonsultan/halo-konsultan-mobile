package com.halokonsultan.mobile.ui.uploaddocument

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.model.ConsultationsDocument
import com.halokonsultan.mobile.data.model.DetailConsultation
import com.halokonsultan.mobile.data.model.dto.DocumentResponse
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
        private val repository: HaloKonsultanRepository
) : ViewModel() {

    private val _upload: MutableLiveData<Resource<DetailConsultation>> = MutableLiveData()
    val upload: LiveData<Resource<DetailConsultation>>
    get() = _upload

    fun uploadDocument(file: File, consultationId: Int, docId: Int) = viewModelScope.launch {
        _upload.postValue(Resource.Loading())
        try {
            val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = requestFile.let { MultipartBody.Part.createFormData("file", file.name, it) }
            val response = repository.uploadDocument(body, consultationId, docId)
            _upload.postValue(Resource.Success(response.body()!!.data))
        } catch (e: Exception) {
            _upload.postValue(Resource.Error(e.message ?: "unknown error"))
        }
    }

}