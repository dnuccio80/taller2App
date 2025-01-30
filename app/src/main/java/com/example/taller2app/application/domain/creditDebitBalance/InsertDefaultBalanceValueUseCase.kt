package com.example.taller2app.application.domain.creditDebitBalance

import com.example.taller2app.application.data.debitCreditBalance.CreditDebitBalanceRepository
import javax.inject.Inject

class InsertDefaultBalanceValueUseCase @Inject constructor(private val creditDebitBalanceRepository: CreditDebitBalanceRepository) {
    suspend operator fun invoke() = creditDebitBalanceRepository.insertDefaultAmount()
}