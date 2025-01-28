package com.example.taller2app.application.domain.payments

import com.example.taller2app.application.data.paymentMethod.PaymentRepository
import javax.inject.Inject

class DeleteAllPaymentDataUseCase @Inject constructor(private val paymentRepository: PaymentRepository) {
    suspend operator fun invoke(){
        paymentRepository.deleteAllPaymentData()
    }
}