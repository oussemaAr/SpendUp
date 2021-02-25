package app.elite.spendup.view.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.elite.spendup.data.pref.PrefsStore
import app.elite.spendup.repository.TransactionRepository


@Deprecated("Not used any more")
@Suppress("UNCHECKED_CAST")
class TransactionViewModelFactory(
    private val transactionRepository: TransactionRepository,
    private val prefsStore: PrefsStore
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.constructors.first().newInstance(transactionRepository, prefsStore) as T
    }
}