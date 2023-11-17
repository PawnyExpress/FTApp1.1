package com.example.ftapp11.data

import kotlinx.coroutines.flow.Flow

class OfflineFInancesRepository(private val incExpDao: IncExpDao) : IncExpRepository {
    override fun getAllItemsStream(): Flow<List<IncExp>> = incExpDao.getAllItems()

    override fun getItemStream(id: Int): Flow<IncExp?> = incExpDao.getItem(id)

    override suspend fun insertItem(item: IncExp) = incExpDao.insert(item)

    override suspend fun deleteItem(item: IncExp) = incExpDao.delete(item)

    override suspend fun updateItem(item: IncExp) = incExpDao.update(item)
}