package com.example.taller2app.application.domain.payments

import com.example.taller2app.application.data.paymentMethod.PaymentRepository
import javax.inject.Inject

class GetAllPaymentsUseCase @Inject constructor(private val paymentRepository: PaymentRepository)  {
    operator fun invoke() = paymentRepository.getAllPayments()
}