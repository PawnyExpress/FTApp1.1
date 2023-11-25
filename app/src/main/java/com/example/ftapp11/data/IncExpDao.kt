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
    suspend fun insert(incExp: IncExp)

    @Update
    suspend fun update(incExp: IncExp)

    @Delete
    suspend fun delete(incExp: IncExp)

    @Query("SELECT * from incExp WHERE id = :id")
    fun getItem(id: Int): Flow<IncExp>

    @Query("SELECT * from incExp ORDER BY name ASC")
    fun getAllItems(): Flow<List<IncExp>>
}