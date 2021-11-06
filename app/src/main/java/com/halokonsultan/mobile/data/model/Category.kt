package com.halokonsultan.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(

        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val logo: String,
        val name: String
)