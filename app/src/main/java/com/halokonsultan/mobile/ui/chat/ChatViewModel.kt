package com.halokonsultan.mobile.ui.chat

import androidx.lifecycle.*
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.Message
import com.halokonsultan.mobile.utils.MESSAGE_TYPE_CONSULTANT
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils.toBoolean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
   private val repository: BaseRepository
): ViewModel() {

    private val _messages: MutableLiveData<Resource<Message>> = MutableLiveData()
    val messages: LiveData<Resource<Message>>
        get() = _messages

    fun getChatList(page: Int) = repository.getAllConversations(repository.getUserId(), page).asLiveData()

    fun getAllMessages(id: Int) = repository.getAllMessages(id).asLiveData()

    fun sendMessage(id: Int, message: String) = viewModelScope.launch {
        _messages.postValue(Resource.Loading())
        try {
            val response = repository.sendMessage(id, repository.getUserId(), message)
            if (response.body() != null && response.body()!!.data != null) {
                _messages.postValue(Resource.Success(response.body()!!.data!!))
                sendNotification(id, message)
            }
        } catch (e: Exception) {
            _messages.postValue(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }

    fun filterReadMessages(messages: List<Message>) =
        messages.filter { it.sender == MESSAGE_TYPE_CONSULTANT && !it.is_read.toBoolean() }
                .forEach { readMessage(it.id) }


    private fun readMessage(id: Int) = viewModelScope.launch {
        repository.readMessage(id)
    }

    private fun sendNotification(id: Int, message: String) = viewModelScope.launch {
        repository.sendNotification(id, repository.getUserName() ?: "User", message)
    }

}