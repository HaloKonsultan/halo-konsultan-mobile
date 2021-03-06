package com.halokonsultan.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.halokonsultan.mobile.base.BaseModel

@Entity(tableName = "consultations")
data class Consultation(

    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    val user_id: Int,
    val consultant_id: Int,
    val name: String,
    val title: String,
    val status: String,
    val is_confirmed: Int,
    val date: String?,
    val time: String?
): BaseModel(id)