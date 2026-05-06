package com.example.arogyasahayalocal.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checkups")
data class Checkup(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: Int,
    val doctorName: String,
    val date: Long,
    val time: String,
    val reason: String,
    val isCompleted: Boolean = false
)
