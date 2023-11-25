package com.example.ftapp11.data

import kotlinx.coroutines.flow.Flow

class OfflineFinancesRepository(private val incExpDao: IncExpDao) : IncExpRepository {
    override fun getAllItemsStream(): Flow<List<IncExp>> = incExpDao.getAllItems()

    override fun getItemStream(id: Int): Flow<IncExp?> = incExpDao.getItem(id)

    override suspend fun insertItem(incExp: IncExp) = incExpDao.insert(incExp)

    override suspend fun deleteItem(incExp: IncExp) = incExpDao.delete(incExp)

    override suspend fun updateItem(incExp: IncExp) = incExpDao.update(incExp)
}