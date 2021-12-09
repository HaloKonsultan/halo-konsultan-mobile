package com.halokonsultan.mobile.ui.chat

import androidx.lifecycle.*
import com.halokonsultan.mobile.base.BaseViewModel
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.model.Message
import com.halokonsultan.mobile.utils.MESSAGE_TYPE_CONSULTANT
import com.halokonsultan.mobile.utils.Utils.toBoolean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
   private val repository: BaseRepository
): BaseViewModel() {

    fun getChatList(page: Int) = repository.getAllConversations(repository.getUserId(), page).asLiveData()

    fun getAllMessages(id: Int) = repository.getAllMessages(id).asLiveData()

    fun sendMessage(id: Int, message: String) = callApiReturnLiveData(
        apiCall = { repository.sendMessage(id, repository.getUserId(), message) },
        handleAfterPostSuccess = { sendNotification(id, message) }
    )

    fun filterReadMessages(messages: List<Message>) =
        messages.filter { it.sender == MESSAGE_TYPE_CONSULTANT && !it.is_read.toBoolean() }
                .forEach { readMessage(it.id) }

    private fun readMessage(id: Int) = callApiReturnLiveData(
        apiCall = { repository.readMessage(id) }
    )

    private fun sendNotification(id: Int, message: String) = viewModelScope.launch {
        repository.sendNotification(id, repository.getUserName() ?: "User", message)
    }
}