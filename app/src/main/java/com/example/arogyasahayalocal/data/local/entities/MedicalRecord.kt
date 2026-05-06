package com.example.arogyasahayalocal.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medical_records")
data class MedicalRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: Int,
    val diagnosis: String,
    val prescription: String,
    val reports: String? = null,
    val notes: String? = null,
    val date: Long
)
