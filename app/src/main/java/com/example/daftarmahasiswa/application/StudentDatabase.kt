package com.example.daftarmahasiswa.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.daftarmahasiswa.dao.StudentDao
import com.example.daftarmahasiswa.model.Student

@Database(entities = [Student::class], version = 2, exportSchema = false)
abstract class StudentDatabase: RoomDatabase() {
    abstract fun  studentDao(): StudentDao

    companion object{
        private var INSTANCE: StudentDatabase? = null

        // migrasi database versi 1 ke 2, karena ada perubahan table tadi
        private val migration1To2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE student_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE student_table ADD COLUMN longitude Double DEFAULT 0.0")
            }

        }

        fun getDatabase(context: Context): StudentDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student_database"
                )
                    .addMigrations(migration1To2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}