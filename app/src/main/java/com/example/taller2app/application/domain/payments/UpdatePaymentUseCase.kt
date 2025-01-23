package com.example.taller2app.application.domain.payments

import com.example.taller2app.application.data.paymentMethod.PaymentRepository
import com.example.taller2app.application.ui.dataClasses.PaymentDataClass
import javax.inject.Inject

class UpdatePaymentUseCase @Inject constructor(private val paymentRepository: PaymentRepository) {
    suspend operator fun invoke(payment:PaymentDataClass) = paymentRepository.updatePayment(payment)
}