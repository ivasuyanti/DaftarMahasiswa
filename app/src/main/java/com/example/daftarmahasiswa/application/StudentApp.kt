package com.example.daftarmahasiswa.application

import android.app.Application
import com.example.daftarmahasiswa.repository.StudentRepository

class StudentApp: Application() {
    val database by lazy { StudentDatabase.getDatabase(this) }
    val repository by lazy { StudentRepository(database.studentDao()) }
}