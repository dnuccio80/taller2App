package com.example.taller2app.application.data.paymentMethod

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taller2app.application.ui.dataClasses.PaymentDataClass

@Entity
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val method: String,
    val amount: Int
)

fun PaymentEntity.toPaymentDataClass() = PaymentDataClass(
    id = id,
    method = method,
    amount = amount
)
