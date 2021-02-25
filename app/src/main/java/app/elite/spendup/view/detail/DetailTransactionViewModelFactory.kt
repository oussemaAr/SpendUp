package app.elite.spendup.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.elite.spendup.repository.TransactionRepository

@Suppress("UNCHECKED_CAST")
class DetailTransactionViewModelFactory(private val transactionRepository: TransactionRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.constructors.first().newInstance(transactionRepository) as T
    }

}