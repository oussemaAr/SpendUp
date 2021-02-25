package app.elite.spendup.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TransactionDAO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TransactionDAOTest