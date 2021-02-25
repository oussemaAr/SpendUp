package app.elite.spendup.view.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.elite.spendup.repository.TagRepository
import app.elite.spendup.repository.TransactionRepository

@Suppress("UNCHECKED_CAST")
class AddTransactionViewModelFactory(
    private val transactionRepository: TransactionRepository,
    private val tagRepository: TagRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.constructors.first()
            .newInstance(transactionRepository, tagRepository) as T
    }

}