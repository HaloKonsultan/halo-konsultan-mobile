package com.halokonsultan.mobile.data.model

data class Chat(
    val id: Int,
    val consultant_id: Int,
    val user_id: Int,
    val is_ended: Int,
    val consultant_photo: String,
    val consultant_name: String,
    val consultant_category: String,
    val last_message: String,
    val last_message_time: String,
    val last_message_has_read: Int
)
