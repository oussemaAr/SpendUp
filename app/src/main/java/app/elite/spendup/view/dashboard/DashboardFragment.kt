package app.elite.spendup.view.dashboard

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.elite.spendup.R
import app.elite.spendup.data.local.AppDatabase
import app.elite.spendup.data.local.entities.TransactionEntity
import app.elite.spendup.data.pref.PrefsStore
import app.elite.spendup.databinding.AddUsernameLayoutBinding
import app.elite.spendup.databinding.DashboardLayoutBinding
import app.elite.spendup.repository.TransactionRepository
import app.elite.spendup.utils.hideView
import app.elite.spendup.utils.showView
import app.elite.spendup.utils.stringText
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var binding: DashboardLayoutBinding
    private val transactionsAdapter = TransactionsAdapter()

    @Inject
    lateinit var myText : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DashboardLayoutBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initView()
        config()
        loadTransactions()
        initRecyclerView()

        Timber.e(myText)
    }

    private fun initRecyclerView() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val transaction = transactionsAdapter.currentList[position]
                val transactionItem = TransactionEntity(
                    id = transaction.id,
                    title = transaction.title,
                    amount = transaction.amount,
                    transactionType = transaction.transactionType,
                    tag = transaction.tag,
                    date = transaction.date,
                    note = transaction.note,
                    createdAt = transaction.createdAt,

                    )
                viewModel.deleteTransaction(transactionItem)
                Snackbar.make(
                    binding.root,
                    getString(R.string.success_transaction_delete),
                    Snackbar.LENGTH_LONG
                )
                    .apply {
                        setAction("Undo") {
                            viewModel.insertTransaction(
                                transactionItem
                            )
                        }
                        show()
                    }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.transactionRecycler)
        }

        transactionsAdapter.setOnItemClickListener {
            val direction =
                DashboardFragmentDirections.actionDashboardFragmentToDetailTransactionFragment(it)
            findNavController().navigate(direction)
        }
    }

    private fun loadTransactions() {
        viewModel.getAllTransactions().asLiveData().observe(viewLifecycleOwner) {
            transactionsAdapter.submitList(it)
            binding.loadingBar.hideView()
            if (it.isEmpty()) {
                binding.emptyLayout.showView()
                binding.addTransaction.showView()
                binding.transactionRecycler.hideView()
            } else {
                binding.emptyLayout.hideView()
                binding.addTransaction.showView()
                binding.transactionRecycler.showView()
            }
        }
    }

    private fun config() {
        lifecycleScope.launchWhenCreated {
            viewModel.getTheme().asLiveData().observe(viewLifecycleOwner) {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
            viewModel.getUsername().asLiveData().observe(viewLifecycleOwner) {
                if (it == null) {
                    binding.welcomeLayout.welcomeText.text =
                        getString(R.string.hello_message, "Anonymous")
                    usernameDialog()
                } else
                    binding.welcomeLayout.welcomeText.text = getString(R.string.hello_message, it)
            }
        }
    }

    private fun usernameDialog() {
        val dialogBinding = AddUsernameLayoutBinding.inflate(layoutInflater)
        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()
        dialogBuilder.setOnShowListener {
            dialogBinding.addTag.setOnClickListener {
                if (dialogBinding.name.stringText().isNotEmpty()) {
                    viewModel.setUsername(
                        dialogBinding.name.stringText().capitalize(Locale.getDefault())
                    )
                    dialogBuilder.dismiss()
                } else {
                    dialogBinding.name.error = getString(R.string.empty_field, "Username")
                }
            }
        }
        dialogBuilder.show()
    }

    private fun initView() {
        with(binding) {
            transactionRecycler.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = transactionsAdapter
            }

            addTransaction.setOnClickListener {
                val direction =
                    DashboardFragmentDirections.actionDashboardFragmentToAddTransactionFragment()
                findNavController().navigate(direction)
            }

            welcomeLayout.root.setOnClickListener {
                usernameDialog()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_night_mode -> viewModel.changeTheme()
            R.id.action_add_tag -> findNavController().navigate(R.id.action_dashboardFragment_to_addTagFragment)
        }
        return true
    }
}