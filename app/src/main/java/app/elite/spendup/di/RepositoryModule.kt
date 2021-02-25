package app.elite.spendup.di

import app.elite.spendup.data.local.AppDatabase
import app.elite.spendup.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideTransactionRepository(database: AppDatabase)
            : TransactionRepository = TransactionRepository(database)

}