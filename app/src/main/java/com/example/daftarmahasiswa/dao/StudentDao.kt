package com.example.daftarmahasiswa.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.daftarmahasiswa.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM `student_table` ORDER BY name ASC")
    fun getAllStudent(): Flow<List<Student>>

    @Insert
    suspend fun insertStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Update fun updateStudent(student: Student)


}