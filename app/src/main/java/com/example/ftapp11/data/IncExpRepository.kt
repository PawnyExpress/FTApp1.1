package com.example.ftapp11.data

import kotlinx.coroutines.flow.Flow

interface IncExpRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllIncExpStream(): Flow<List<IncExp>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getIncExpStream(id: Int): Flow<IncExp?>

    /**
     * Insert item in the data source
     */
    suspend fun insertIncome(incExp: IncExp)

    /**
     * Delete item from the data source
     */
    suspend fun deleteIncome(incExp: IncExp)

    /**
     * Update item in the data source
     */
    suspend fun updateIncome(incExp: IncExp)
    /**
     * Insert expense in the data source
     */
    suspend fun insertExpense(incExp: IncExp)

    /**
     * Delete expense from the data source
     */
    suspend fun deleteExpense(incExp: IncExp)

    /**
     * Update expense in the data source
     */
    suspend fun updateExpense(incExp: IncExp)
}