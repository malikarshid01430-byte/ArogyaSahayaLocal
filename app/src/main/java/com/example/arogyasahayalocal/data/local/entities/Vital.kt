package com.example.arogyasahayalocal.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals")
data class Vital(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: Int,
    val systolicBP: Int? = null,
    val diastolicBP: Int? = null,
    val bloodSugar: Double? = null,
    val heartRate: Int? = null,
    val date: Long
)
