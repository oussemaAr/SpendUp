package app.elite.spendup.view.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import app.elite.spendup.R
import app.elite.spendup.data.local.AppDatabase
import app.elite.spendup.data.local.entities.TagEntity
import app.elite.spendup.data.local.entities.TransactionEntity
import app.elite.spendup.databinding.AddTransactionLayoutBinding
import app.elite.spendup.repository.TagRepository
import app.elite.spendup.repository.TransactionRepository
import app.elite.spendup.utils.Constants
import app.elite.spendup.utils.parseDouble
import app.elite.spendup.utils.stringText
import app.elite.spendup.utils.transformIntoDatePicker
import java.util.*


class AddTransactionFragment : Fragment() {

    private val viewModel: AddTransactionViewModel by viewModels {
        AddTransactionViewModelFactory(
            TransactionRepository(AppDatabase.getInstance(requireContext())),
            TagRepository(AppDatabase.getInstance(requireContext())),
        )
    }
    private lateinit var binding: AddTransactionLayoutBinding
    private lateinit var selectedItem: TagEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddTransactionLayoutBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lifecycleScope.launchWhenResumed {
            viewModel.getTags().observe(viewLifecycleOwner) { list ->
                val tagsAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_filter_dropdown,
                    list
                )
                binding.tag.setAdapter(tagsAdapter)
            }
        }

        with(binding) {
            val transactionTypeAdapter =
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_filter_dropdown,
                    Constants.transactionType
                )

            transactionType.setAdapter(transactionTypeAdapter)
            tag.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    selectedItem = tag.adapter.getItem(position) as TagEntity
                }

            date.transformIntoDatePicker(
                requireContext(),
                "dd/MM/yyyy",
                Date()
            )
            btnSaveTransaction.setOnClickListener {
                binding.apply {
                    val (_, title, amount, transactionType, tag, date, note) = getTransactionContent()
                    when {
                        title.isEmpty() -> {
                            this.title.error = getString(R.string.empty_field, "Title")
                        }
                        amount.isNaN() -> {
                            this.amount.error = getString(R.string.empty_field, "amount")
                        }
                        transactionType.isEmpty() -> {
                            this.transactionType.error =
                                getString(R.string.empty_field, "Transaction type")
                        }
                        tag.name.isEmpty() -> {
                            this.tag.error = getString(R.string.empty_field, "tag")
                        }
                        date.isEmpty() -> {
                            this.date.error = getString(R.string.empty_field, "Date")
                        }
                        note.isEmpty() -> {
                            this.note.error = getString(R.string.empty_field, "Note")
                        }
                        else -> {
                            viewModel.insertTransaction(getTransactionContent()).run {
                                findNavController().popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getTransactionContent(): TransactionEntity = binding.let {
        val title = it.title.stringText()
        val amount = parseDouble(it.amount.stringText())
        val transactionType = it.transactionType.stringText()
        val date = it.date.stringText()
        val note = it.note.stringText()

        return TransactionEntity(
            title = title,
            amount = amount,
            transactionType = transactionType,
            tag = selectedItem,
            date = date,
            note = note
        )
    }

}