package com.example.taller2app.application.ui.dataClasses

import java.text.NumberFormat
import java.util.Locale

data class PaymentReceivedDataClass(
    val description: String,
    val amount: Int
)

fun PaymentReceivedDataClass.formatNumber(value: Int): String {
    val formatter = NumberFormat.getInstance(Locale("es", "AR"))
    return formatter.format(value)
}
