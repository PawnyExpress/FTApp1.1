package com.example.ftapp11.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [IncExp::class], version = 1, exportSchema = false)
abstract class IncExpDatabase : RoomDatabase(){

    abstract fun incexpDao(): IncExpDao

    companion object {
        @Volatile
        private var Instance: IncExpDatabase? = null

        fun getDatabase(context: Context): IncExpDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, IncExpDatabase::class.java, "incexp_database")
                    .build().also { Instance = it}
            }
        }
    }
}