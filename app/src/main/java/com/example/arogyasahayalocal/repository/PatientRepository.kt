package com.example.arogyasahayalocal.repository

import com.example.arogyasahayalocal.data.local.dao.PatientDao
import com.example.arogyasahayalocal.data.local.entities.Patient
import kotlinx.coroutines.flow.Flow

class PatientRepository(private val patientDao: PatientDao) {
    val allPatients: Flow<List<Patient>> = patientDao.getAllPatients()

    suspend fun addPatient(patient: Patient) = patientDao.insertPatient(patient)
    suspend fun updatePatient(patient: Patient) = patientDao.updatePatient(patient)
    suspend fun deletePatient(patient: Patient) = patientDao.deletePatient(patient)
    suspend fun getPatientById(patientId: Int): Patient? = patientDao.getPatientById(patientId)
}
