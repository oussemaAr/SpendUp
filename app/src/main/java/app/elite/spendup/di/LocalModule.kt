package app.elite.spendup.di

import android.content.Context
import app.elite.spendup.data.local.AppDatabase
import app.elite.spendup.data.local.dao.TransactionDao
import app.elite.spendup.data.pref.PrefsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Provides
    fun provideTransactionDao(database: AppDatabase):
            TransactionDao = database.getTransactionDao()

    @Provides
    fun provideTransactionDaoTest(database: AppDatabase): TransactionDao =
        database.getTransactionDao()

    @Provides
    fun providePreference(@ApplicationContext context: Context):
            PrefsStore = PrefsStore(context)

    @Provides
    fun provideString(): String = "Tester"

}