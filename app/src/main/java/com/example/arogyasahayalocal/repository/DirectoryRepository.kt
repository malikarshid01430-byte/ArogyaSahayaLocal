package com.example.arogyasahayalocal.repository

import com.example.arogyasahayalocal.data.local.dao.DirectoryDao
import com.example.arogyasahayalocal.data.local.entities.Doctor
import com.example.arogyasahayalocal.data.local.entities.Staff
import kotlinx.coroutines.flow.Flow

class DirectoryRepository(private val directoryDao: DirectoryDao) {
    val allDoctors: Flow<List<Doctor>> = directoryDao.getAllDoctors()
    val allStaff: Flow<List<Staff>> = directoryDao.getAllStaff()

    suspend fun addDoctor(doctor: Doctor) = directoryDao.insertDoctor(doctor)
    suspend fun addStaff(staff: Staff) = directoryDao.insertStaff(staff)
}
