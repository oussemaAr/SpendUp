package app.elite.spendup.view.detail

import androidx.lifecycle.ViewModel
import app.elite.spendup.repository.TransactionRepository

class DetailTransactionViewModel(private val transactionRepository: TransactionRepository) :
    ViewModel() {

    fun details(id: Int) =
        transactionRepository.getTransactionByID(id)

}