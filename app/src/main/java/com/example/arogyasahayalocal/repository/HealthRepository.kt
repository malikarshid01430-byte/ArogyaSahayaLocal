package com.example.arogyasahayalocal.repository

import com.example.arogyasahayalocal.data.local.dao.HealthDao
import com.example.arogyasahayalocal.data.local.entities.*
import kotlinx.coroutines.flow.Flow

class HealthRepository(private val healthDao: HealthDao) {
    fun getRecords(patientId: Int): Flow<List<MedicalRecord>> = healthDao.getRecordsForPatient(patientId)
    suspend fun addRecord(record: MedicalRecord) = healthDao.insertRecord(record)

    fun getActiveMedicines(patientId: Int): Flow<List<Medicine>> = healthDao.getActiveMedicinesForPatient(patientId)
    suspend fun addMedicine(medicine: Medicine) = healthDao.insertMedicine(medicine)
    suspend fun updateMedicine(medicine: Medicine) = healthDao.updateMedicine(medicine)

    fun getCheckups(patientId: Int): Flow<List<Checkup>> = healthDao.getCheckupsForPatient(patientId)
    suspend fun addCheckup(checkup: Checkup) = healthDao.insertCheckup(checkup)

    fun getRecentVitals(patientId: Int): Flow<List<Vital>> = healthDao.getRecentVitalsForPatient(patientId)
    suspend fun addVital(vital: Vital) = healthDao.insertVital(vital)
}
