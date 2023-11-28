package com.example.ftapp11.data

import kotlinx.coroutines.flow.Flow

class OfflineFinancesRepository(private val incExpDao: IncExpDao) : IncExpRepository {
    override fun getAllIncExpStream(): Flow<List<IncExp>> = incExpDao.getAllItems()

    override fun getIncExpStream(id: Int): Flow<IncExp?> = incExpDao.getItem(id)

    override suspend fun insertIncome(income: IncExp) = incExpDao.insert(income)

    override suspend fun deleteIncome(income: IncExp) = incExpDao.delete(income)

    override suspend fun updateIncome(income: IncExp) = incExpDao.update(income)

//    override suspend fun insertExpense(expense: IncExp) = incExpDao.insert(expense)
//
//    override suspend fun deleteExpense(expense: IncExp) = incExpDao.delete(expense)
//
//    override suspend fun updateExpense(expense: IncExp) = incExpDao.update(expense)
}