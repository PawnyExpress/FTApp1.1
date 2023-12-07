package com.example.ftapp11.data

import kotlinx.coroutines.flow.Flow

class OfflineFinancesRepository(private val incExpDao: IncExpDao) : IncExpRepository {
    override fun getAllIncExpStream(): Flow<List<IncExp>> = incExpDao.getAllItems()

    override fun getIncExpStream(id: Int): Flow<IncExp?> = incExpDao.getItem(id)

    override suspend fun insertIncome(incExp: IncExp) = incExpDao.insert(incExp)

    override suspend fun deleteIncome(incExp: IncExp) = incExpDao.delete(incExp)

    override suspend fun updateIncome(incExp: IncExp) = incExpDao.update(incExp)

//    override suspend fun insertExpense(expense: IncExp) = incExpDao.insert(expense)
//
//    override suspend fun deleteExpense(expense: IncExp) = incExpDao.delete(expense)
//
//    override suspend fun updateExpense(expense: IncExp) = incExpDao.update(expense)
}