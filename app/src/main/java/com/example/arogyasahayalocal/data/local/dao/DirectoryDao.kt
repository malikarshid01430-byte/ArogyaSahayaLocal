package com.example.arogyasahayalocal.data.local.dao

import androidx.room.*
import com.example.arogyasahayalocal.data.local.entities.Doctor
import com.example.arogyasahayalocal.data.local.entities.Staff
import kotlinx.coroutines.flow.Flow

@Dao
interface DirectoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: Doctor)

    @Query("SELECT * FROM doctors")
    fun getAllDoctors(): Flow<List<Doctor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaff(staff: Staff)

    @Query("SELECT * FROM staff")
    fun getAllStaff(): Flow<List<Staff>>
}
