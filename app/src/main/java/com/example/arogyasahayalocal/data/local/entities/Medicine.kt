package com.example.arogyasahayalocal.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: Int,
    val name: String,
    val dosage: String,
    val frequency: String, // e.g., "Daily", "Weekly"
    val isMorning: Boolean,
    val isAfternoon: Boolean,
    val isNight: Boolean,
    val startDate: Long,
    val endDate: Long? = null,
    val isActive: Boolean = true
)
