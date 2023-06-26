package com.example.daftarmahasiswa.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.daftarmahasiswa.model.Student
import com.example.daftarmahasiswa.repository.StudentRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class StudentViewModel(private val repository: StudentRepository): ViewModel() {
    val allstudents: LiveData<List<Student>> = repository.allStudents.asLiveData()

    fun insert(student: Student) = viewModelScope.launch {
        repository.insertStudent(student)
    }

    fun delete(student: Student) = viewModelScope.launch {
        repository.deleteStudent(student)
    }

    fun update(student: Student) = viewModelScope.launch {
        repository.updateStudent(student)
    }
}

class StudentViewModelFactory(private  val repository: StudentRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((StudentViewModel::class.java))) {
            return StudentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}