package com.example.arogyasahayalocal.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "staff")
data class Staff(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val role: String, // e.g., "ASHA Worker", "Nurse"
    val contactNumber: String,
    val ranking: Double = 0.0,
    val post: String = "" // Designation or Grade
)
