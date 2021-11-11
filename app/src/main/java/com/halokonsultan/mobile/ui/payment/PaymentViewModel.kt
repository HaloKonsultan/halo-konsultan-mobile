package com.halokonsultan.mobile.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.Transaction
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
        private val repository: BaseRepository
): ViewModel() {

    private val _transaction: MutableLiveData<Resource<Transaction>> = MutableLiveData()
    val transaction: LiveData<Resource<Transaction>>
        get() = _transaction

    fun getDetailTransaction(id: Int) = viewModelScope.launch {
        _transaction.postValue(Resource.Loading())
        try {
            val response = repository.getTransaction(id)
            if (response.body() != null) {
                _transaction.postValue(Resource.Success(response.body()!!.data!!))
            }
        } catch (e: Exception) {
            _transaction.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }
}