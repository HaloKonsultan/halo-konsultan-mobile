package com.halokonsultan.mobile.ui.chat

import androidx.lifecycle.ViewModel
import com.halokonsultan.mobile.data.BaseRepository
import javax.inject.Inject

class ChatViewModel @Inject constructor(
   private val repository: BaseRepository
): ViewModel() {
}