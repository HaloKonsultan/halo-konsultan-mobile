package com.halokonsultan.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val forum_id: Int,
    val sender: String,
    val message: String,
    val is_read: Int,
    val time: String
)
