package com.example.taller2app.application.data.debitCreditBalance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CreditDebitBalanceEntity(
    @PrimaryKey
    val amount: Int
)
