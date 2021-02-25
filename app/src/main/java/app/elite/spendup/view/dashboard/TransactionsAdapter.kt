package app.elite.spendup.view.dashboard

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.elite.spendup.R
import app.elite.spendup.data.local.entities.TransactionEntity
import app.elite.spendup.databinding.ItemTransactionLayoutBinding
import app.elite.spendup.utils.thumbnail


class TransactionsAdapter :
    ListAdapter<TransactionEntity, TransactionsAdapter.TransactionsViewHolder>(TransactionDiffUtils()) {

    class TransactionDiffUtils : DiffUtil.ItemCallback<TransactionEntity>() {
        override fun areItemsTheSame(
            oldItem: TransactionEntity,
            newItem: TransactionEntity
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: TransactionEntity,
            newItem: TransactionEntity
        ) = oldItem == newItem
    }

    inner class TransactionsViewHolder(private val binding: ItemTransactionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(transactionEntity: TransactionEntity) {
            with(binding) {

                transactionTitle.text = transactionEntity.title
                transactionAmount.text = transactionEntity.amount.toString()
                transactionCategory.text = transactionEntity.tag.name
                transactionDate.text =
                    DateUtils.getRelativeTimeSpanString(transactionEntity.createdAt)
                transactionIcon.thumbnail(transactionEntity.tag.thumbnail)

                when (transactionEntity.transactionType) {
                    "Income" -> {
                        transactionAmount.setTextColor(
                            ContextCompat.getColor(
                                transactionAmount.context,
                                R.color.income
                            )
                        )

                        transactionAmount.text = "+ $${transactionEntity.amount}"
                    }
                    "Expense" -> {
                        transactionAmount.setTextColor(
                            ContextCompat.getColor(
                                transactionAmount.context,
                                R.color.expense
                            )
                        )
                        transactionAmount.text = "- $${transactionEntity.amount}"
                    }
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val binding = ItemTransactionLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(item.id)
            }
        }
        holder.bind(item)
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }
}