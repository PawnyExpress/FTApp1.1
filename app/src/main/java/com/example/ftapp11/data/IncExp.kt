package com.example.ftapp11.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tablename = "finances")
data class IncExp (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val amount: Double,
)