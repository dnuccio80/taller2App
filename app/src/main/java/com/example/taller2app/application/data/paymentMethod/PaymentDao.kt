package com.example.taller2app.application.data.paymentMethod

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {

    @Query("SELECT * FROM PaymentEntity")
    fun getAllPayments(): Flow<List<PaymentEntity>>

    @Query("DELETE FROM PAYMENTENTITY")
    suspend fun deleteAllPaymentData()

    @Insert
    suspend fun addNewPayment(paymentDataEntity: PaymentEntity)

    @Delete
    suspend fun deletePayment(paymentDataEntity: PaymentEntity)

    @Update
    suspend fun updatePayment(paymentDataEntity: PaymentEntity)

}