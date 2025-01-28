package com.example.taller2app.application.data.paymentMethod

import com.example.taller2app.application.ui.dataClasses.PaymentDataClass
import com.example.taller2app.application.ui.dataClasses.toPaymentDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PaymentRepository @Inject constructor(private val paymentDao: PaymentDao) {

    fun getAllPayments(): Flow<List<PaymentDataClass>> = paymentDao.getAllPayments().map {
        it.map {
            entity -> entity.toPaymentDataClass()
        }
    }

    suspend fun deleteAllPaymentData(){
        paymentDao.deleteAllPaymentData()
    }

    suspend fun addNewPayment(paymentDataClass: PaymentDataClass){
        paymentDao.addNewPayment(paymentDataClass.toPaymentDataEntity())
    }

    suspend fun deletePayment(paymentDataClass: PaymentDataClass){
        paymentDao.deletePayment(paymentDataClass.toPaymentDataEntity())
    }

    suspend fun updatePayment(paymentDataClass: PaymentDataClass){
        paymentDao.updatePayment(paymentDataClass.toPaymentDataEntity())
    }

}