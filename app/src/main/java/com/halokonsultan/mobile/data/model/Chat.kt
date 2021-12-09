package com.halokonsultan.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.halokonsultan.mobile.base.BaseModel

@Entity(tableName = "chats")
data class Chat(

    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    val consultant_id: Int,
    val user_id: Int,
    val is_ended: Int,
    val photo: String,
    val name: String,
    val category: String,
    val last_message: String?,
    val last_messages_time: String?,
    val last_messages_is_read: Int?,
    val last_messages_from: String?
): BaseModel(id)