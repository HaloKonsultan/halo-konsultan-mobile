package com.halokonsultan.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class Profile(

        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val name: String,
        val email: String,
        val city: String,
        val province: String,
        val photo: String?,
        val firebase_id: String?
)