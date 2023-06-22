package com.example.daftarmahasiswa.repository

import com.example.daftarmahasiswa.dao.StudentDao
import com.example.daftarmahasiswa.model.Student
import kotlinx.coroutines.flow.Flow

class StudentRepository(private val studentDao: StudentDao) {
    val allStudent: Flow<List<Student>> = studentDao.getAllStudent()

    suspend fun insertStudent(student: Student) {
        studentDao.insertStudent(student)
    }

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteStudent(student)
    }

    suspend fun updateStudent(student: Student) {
        studentDao.updateStudent(student)
    }
}