package app.elite.spendup.view.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.elite.spendup.data.local.entities.TransactionEntity
import app.elite.spendup.data.pref.PrefsStore
import app.elite.spendup.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
    @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val prefsStore: PrefsStore
) : ViewModel() {

    fun getAllTransactions() = transactionRepository.getTransactions()

    fun insertTransaction(transaction: TransactionEntity) = viewModelScope.launch {
        transactionRepository.insert(transaction)
    }

    fun deleteTransaction(transactionItem: TransactionEntity) = viewModelScope.launch {
        transactionRepository.delete(transactionItem)
    }

    fun changeTheme() = viewModelScope.launch(IO) {
        prefsStore.toggleNightMode()
    }

    fun getTheme() = prefsStore.isNightMode()

    fun getUsername() = prefsStore.getUsername()

    fun setUsername(username: String) = viewModelScope.launch(IO) {
        prefsStore.setUsername(username)
    }

}