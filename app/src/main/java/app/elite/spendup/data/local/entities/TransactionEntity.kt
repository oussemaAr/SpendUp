package app.elite.spendup.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "transactions_table")
data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "amount") var amount: Double,
    @ColumnInfo(name = "transactionType") var transactionType: String,
    @Embedded var tag: TagEntity,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "note") var note: String,
    @ColumnInfo(name = "createdAt") var createdAt: Long = System.currentTimeMillis(),
) {
    val createdAtDateFormat: String
        get() = DateFormat.getDateTimeInstance()
            .format(createdAt)
}
