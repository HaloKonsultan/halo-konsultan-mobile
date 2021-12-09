package com.halokonsultan.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.halokonsultan.mobile.base.BaseModel

@Entity(tableName = "consultants")
data class Consultant(

        @PrimaryKey(autoGenerate = false)
        override val id: Int,
        val name: String,
        val position: String,
        val likes_total: Int?,
        val city: String?,
        val photo: String,
        val category_id: Int
): BaseModel(id)