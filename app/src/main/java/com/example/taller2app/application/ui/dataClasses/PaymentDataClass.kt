package com.example.taller2app.application.ui.dataClasses

import com.example.taller2app.application.data.paymentMethod.PaymentEntity
import java.text.NumberFormat
import java.util.Locale

data class PaymentDataClass(
    val id:Int = 0,
    val method: String,
    val amount: Int
)

fun PaymentDataClass.formatNumber(value: Int): String {
    val formatter = NumberFormat.getInstance(Locale("es", "AR"))
    return formatter.format(value)
}

fun PaymentDataClass.toPaymentDataEntity() = PaymentEntity(
    id = id,
    method = method,
    amount = amount
)

