package com.example.ftapp11.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IncExpDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: IncExp)

    @Update
    suspend fun update(item: IncExp)

    @Delete
    suspend fun delete(item: IncExp)

    @Query("SELECT * from incexp WHERE id = :id")
    fun getItem(id: Int): Flow<IncExp>

    @Query("SELECT * from incexp ORDER BY name ASC")
    fun getAllItems(): Flow<List<IncExp>>
}