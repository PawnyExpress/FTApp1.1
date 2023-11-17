package com.example.ftapp11.data

import kotlinx.coroutines.flow.Flow

interface IncExpRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<IncExp>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<IncExp?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: IncExp)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: IncExp)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: IncExp)
}