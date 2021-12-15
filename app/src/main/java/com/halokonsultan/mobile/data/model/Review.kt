package com.halokonsultan.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val consultationId: Int,
    val userId: Int,
    val hasReviewed: Boolean
)
