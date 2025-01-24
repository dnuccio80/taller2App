package com.example.taller2app.application.ui

sealed class AvailablePaymentMethods(
    val paymentMethod: String
) {
    data object Cash : AvailablePaymentMethods("Cash")
    data object Check : AvailablePaymentMethods("Check")
}