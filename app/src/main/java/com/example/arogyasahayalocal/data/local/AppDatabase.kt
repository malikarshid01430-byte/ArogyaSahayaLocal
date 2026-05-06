package com.example.arogyasahayalocal.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.arogyasahayalocal.data.local.dao.*
import com.example.arogyasahayalocal.data.local.entities.*

@Database(
    entities = [
        User::class,
        Patient::class,
        MedicalRecord::class,
        Medicine::class,
        Checkup::class,
        Vital::class,
        Doctor::class,
        Staff::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun patientDao(): PatientDao
    abstract fun healthDao(): HealthDao
    abstract fun directoryDao(): DirectoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "arogya_sahaya_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
