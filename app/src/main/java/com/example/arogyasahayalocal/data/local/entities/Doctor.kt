package com.example.arogyasahayalocal.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctors")
data class Doctor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val specialization: String,
    val contactNumber: String,
    val location: String,
    val ranking: Double = 0.0, // 0 to 5 stars
    val post: String = "" // Designation, e.g., "Senior Consultant"
)
