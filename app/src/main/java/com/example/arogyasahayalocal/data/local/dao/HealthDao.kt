package com.example.arogyasahayalocal.data.local.dao

import androidx.room.*
import com.example.arogyasahayalocal.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {
    // Medical Records
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: MedicalRecord)

    @Query("SELECT * FROM medical_records WHERE patientId = :patientId ORDER BY date DESC")
    fun getRecordsForPatient(patientId: Int): Flow<List<MedicalRecord>>

    // Medicines
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: Medicine)

    @Query("SELECT * FROM medicines WHERE patientId = :patientId AND isActive = 1")
    fun getActiveMedicinesForPatient(patientId: Int): Flow<List<Medicine>>

    @Update
    suspend fun updateMedicine(medicine: Medicine)

    // Checkups
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckup(checkup: Checkup)

    @Query("SELECT * FROM checkups WHERE patientId = :patientId ORDER BY date ASC")
    fun getCheckupsForPatient(patientId: Int): Flow<List<Checkup>>

    // Vitals
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVital(vital: Vital)

    @Query("SELECT * FROM vitals WHERE patientId = :patientId ORDER BY date DESC LIMIT 7")
    fun getRecentVitalsForPatient(patientId: Int): Flow<List<Vital>>
}
