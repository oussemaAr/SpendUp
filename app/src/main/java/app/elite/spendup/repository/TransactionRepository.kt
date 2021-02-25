package app.elite.spendup.repository

import app.elite.spendup.data.local.AppDatabase
import app.elite.spendup.data.local.entities.TransactionEntity

class TransactionRepository(private val database: AppDatabase) {

    suspend fun insert(transaction: TransactionEntity) =
        database.getTransactionDao().insert(transaction)

    suspend fun delete(transaction: TransactionEntity) =
        database.getTransactionDao().deleteTransaction(transaction)

    fun getTransactionByID(id: Int) = database.getTransactionDao().getTransactionByID(id)

    suspend fun deleteByID(id: Int) = database.getTransactionDao().deleteTransactionByID(id)

    fun getTransactions() = database.getTransactionDao().getAllTransactions()

}