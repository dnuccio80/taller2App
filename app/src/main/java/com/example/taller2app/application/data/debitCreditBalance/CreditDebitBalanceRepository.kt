package com.example.taller2app.application.data.debitCreditBalance

import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.singleOrNull
import javax.inject.Inject

class CreditDebitBalanceRepository @Inject constructor(private val creditDebitBalanceDao: CreditDebitBalanceDao) {

    fun getCurrentAmount(): Flow<Int> = creditDebitBalanceDao.getCurrentAmount()

    suspend fun insertDefaultAmount() = creditDebitBalanceDao.insertInitialAmount()

    suspend fun incrementAmount(value: Int) = creditDebitBalanceDao.incrementAmount(value)

    suspend fun decrementAmount(value: Int) = creditDebitBalanceDao.decrementAmount(value)

    suspend fun clearAmount() = creditDebitBalanceDao.clearAmount()

}