package com.halokonsultan.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.halokonsultan.mobile.base.BaseModel

@Entity(tableName = "categories")
data class Category(

        @PrimaryKey(autoGenerate = false)
        override val id: Int,
        val logo: String,
        val name: String
): BaseModel(id)