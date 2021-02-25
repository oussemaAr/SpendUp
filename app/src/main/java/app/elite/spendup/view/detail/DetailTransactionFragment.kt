package app.elite.spendup.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import app.elite.spendup.data.local.AppDatabase
import app.elite.spendup.databinding.TransactionDetailsLayoutBinding
import app.elite.spendup.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers.Main

class DetailTransactionFragment : Fragment() {

    private val args: DetailTransactionFragmentArgs by navArgs()
    private val viewModel: DetailTransactionViewModel by viewModels {
        DetailTransactionViewModelFactory(
            TransactionRepository(AppDatabase.getInstance(requireContext()))
        )
    }
    private lateinit var binding: TransactionDetailsLayoutBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TransactionDetailsLayoutBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenCreated {
            viewModel.details(args.id).asLiveData(Main).observe(viewLifecycleOwner) {
                with(binding) {
                    title.text = it.title
                    amount.text = it.amount.toString()
                    type.text = it.transactionType
                    tag.text = it.tag.name
                    date.text = it.date
                    note.text = it.note
                    createdAt.text = it.createdAtDateFormat
                }
            }
        }
    }
}