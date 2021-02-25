package app.elite.spendup.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.elite.spendup.data.local.dao.TagDao
import app.elite.spendup.data.local.dao.TransactionDao
import app.elite.spendup.data.local.entities.TagEntity
import app.elite.spendup.data.local.entities.TransactionEntity

@Database(
    entities = [TransactionEntity::class, TagEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTransactionDao(): TransactionDao
    abstract fun getTagDao(): TagDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "transaction.db"
            ).build()
        }
    }
}
