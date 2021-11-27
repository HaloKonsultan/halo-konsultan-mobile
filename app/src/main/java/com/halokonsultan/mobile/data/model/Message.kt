package com.halokonsultan.mobile.data.model

data class Message(
    val id: Int,
    val chat_id: Int,
    val sender: String,
    val message: String,
    val is_read: Int,
    val time: String
)
