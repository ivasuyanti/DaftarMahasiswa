package com.example.daftarmahasiswa.model

import android.bluetooth.BluetoothClass.Device.Major
import android.os.Parcelable
import androidx.dynamicanimation.animation.SpringForce
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.lang.ClassCastException
import java.net.Inet4Address
@Parcelize
@Entity(tableName = "student_table")
data class Student (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val major: String,
    val clazz: String,
    val address: String
        ) : Parcelable
