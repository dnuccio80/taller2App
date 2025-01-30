package com.example.taller2app.application.domain.creditDebitBalance

import com.example.taller2app.application.data.debitCreditBalance.CreditDebitBalanceRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrentCreditDebitBalanceUseCase @Inject constructor(private val creditDebitBalanceRepository: CreditDebitBalanceRepository)  {
    operator fun invoke() = creditDebitBalanceRepository.getCurrentAmount().map { it }
}