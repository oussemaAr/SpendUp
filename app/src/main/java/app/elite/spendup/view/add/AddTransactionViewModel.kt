package app.elite.spendup.view.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.elite.spendup.data.local.entities.TransactionEntity
import app.elite.spendup.repository.TagRepository
import app.elite.spendup.repository.TransactionRepository
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val tagRepository: TagRepository
) :
    ViewModel() {

    fun insertTransaction(transaction: TransactionEntity) = viewModelScope.launch {
        transactionRepository.insert(transaction)
    }

    suspend fun getTags() = tagRepository.getAllTags().asLiveData()

}