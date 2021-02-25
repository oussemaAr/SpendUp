package app.elite.spendup.data.local.dao

import androidx.room.*
import app.elite.spendup.data.local.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transaction: List<TransactionEntity>)

    @Query("SELECT * FROM transactions_table ORDER by createdAt DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions_table WHERE transactionType == :transactionType ORDER by createdAt DESC")
    fun getTransactionsByType(transactionType: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions_table WHERE id = :id")
    fun getTransactionByID(id: Int): Flow<TransactionEntity>

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions_table WHERE id = :id")
    suspend fun deleteTransactionByID(id: Int)
}
